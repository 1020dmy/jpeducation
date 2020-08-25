package com.mantis.im_service.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantis_im_sdk.MIMManager;
import com.example.mantis_im_sdk.bean.ReceiveEntity;
import com.example.mantis_im_sdk.callback.MIMCallBack;
import com.example.mantis_im_sdk.common.ChatType;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mantis.im_service.R;
import com.mantis.im_service.ui.adapter.AdapterOnClickListener;
import com.mantis.im_service.ui.adapter.ChatListAdapter;
import com.mantis.im_service.ui.base.BaseActivity;
import com.mantis.im_service.bean.ShowBigPicBean;
import com.example.mantis_im_sdk.bean.ChatEntity;
import com.mantis.im_service.common.BigImgLoader;
import com.mantis.im_service.common.GlideEngine;
import com.mantis.im_service.ui.callback.MantisViewCB;
import com.mantis.im_service.common.MantisCommon;
import com.mantis.im_service.ui.dialog.ChatDialog;
import com.example.mantis_im_sdk.face.Emoji;
import com.example.mantis_im_sdk.face.EmojiUtil;
import com.example.mantis_im_sdk.face.FaceFragment;
import com.mantis.im_service.ui.module.RecordRobot;
import com.mantis.im_service.ui.presenter.ChatPresenter;
import com.mantis.im_service.util.BitmapUtil;
import com.mantis.im_service.util.EditTextUtils;
import com.mantis.im_service.util.KeyBoardUtils;
import com.mantis.im_service.util.PermissionUtils;
import com.orhanobut.logger.Logger;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.mantis.im_service.util.EditTextUtils.isShouldHideInput;

public class ChatActivity extends BaseActivity<ChatPresenter> implements View.OnClickListener, FaceFragment.OnEmojiClickListener, MantisViewCB<ChatPresenter> {
    private static final String TAG = "ChatActivity";
    //欢迎语消息标识
    private final int WELCOMES_FLAG = 1;
    //模拟咨询师正在输入标识
    private final int UPDATE_FLAG = 2;

    private RecyclerView mChatList;
    private EditText mChatEt;
    /**
     * 表情
     */
    private ImageView mChatFace;
    /**
     * 更多
     */
    private ImageView mChatImg;
    private ImageView iv_back;
    private TextView sendButton;
    private TextView title;
    private ChatListAdapter adapter;
    private FrameLayout mChatFaceFragment;
    private long recordTimestamp = 0;
    private boolean isChat;//标记双方是否开始对话
    //欢迎语计时器
    private Timer welTimer;
    //自动追问计时器
    private Timer autoReqTimer;
    private Timer firstTimer;
    //拉取机器人消息类
    private RecordRobot robot;
    //自动拉取消息数量
    private int autoCount;
    //记录最后一次输入时间
    private long inputlastTime;
    //是否完成对话
    private boolean isComplete;
    private boolean isConnectSucc;
    private String agentName;
    private boolean isFirstChat;
    private ChatDialog timeOutDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WELCOMES_FLAG:
                    if (msg == null) {
                        return;
                    }
                    ChatEntity chatEntity = (ChatEntity) msg.obj;
                    if (chatEntity == null) {
                        return;
                    }
                    addMessage(chatEntity);
                    //第一条数据只展示不发送服务器
                    if (msg.arg1 == 0) {
                        return;
                    }
                    mPresenter.sendMsg(chatEntity);
                    break;
                case UPDATE_FLAG:
                    String str = (String) msg.obj;
                    title.setText(str);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        examinePermission();
        initView();
        init();
    }

    private void examinePermission() {
        List<String> list = PermissionUtils.checkPermission(this, MantisCommon.perms);
        if (list.size() > 0) {
            PermissionUtils.requestPer(this, list, MantisCommon.REQ_CODE_PERMISSION);
        } else {
            showLoading();
            login();
        }
    }

    private void login() {
        MIMManager.getInstance().login(new MIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                closeLoading();
                Logger.d("登录失败 code = %s  desc = %s", code, desc, TAG);
                runOnUiThread(() -> {
                    new ChatDialog(ChatActivity.this)
                            .setTitle("提示")
                            .setMessage("建立连接失败,请重新建立连接!")
                            .setSingle(true)
                            .setOnClickBottomListener(new ChatDialog.OnClickBottomListener() {
                                @Override
                                public void onConfirmClick() {
                                    finish();
                                }

                                @Override
                                public void onCancelClick() {

                                }
                            }).show();
                });
            }

            @Override
            public void onSuccess() {
                Logger.d("登录成功", TAG);
                //建立首次对话
                isFirstChat = true;
                buildSendMessage("", "", "", ChatType.SDK_CHAT_START);
            }
        });
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        mChatList = findViewById(R.id.chat_list);
        mChatEt = findViewById(R.id.chat_content_et);
        mChatFace = findViewById(R.id.chat_face);
        mChatImg = findViewById(R.id.chat_img);
        sendButton = findViewById(R.id.send);
        mChatFaceFragment = findViewById(R.id.chat_face_fragment);

        title = findViewById(R.id.tv_title);
        mChatImg.setOnClickListener(this);
        mChatFace.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        //表情展示
        FaceFragment instance = FaceFragment.Instance();
        addFragment(getSupportFragmentManager(), instance.getClass(), R.id.chat_face_fragment, null);
        listener();
    }

    private void listener() {
        //adapter 回调
        getListAdapter().setClickListener(new AdapterOnClickListener() {
            @Override
            public void recMsgOnClick(ReceiveEntity.ContentBean contentBean) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("isChoiceRes", true);
                    jsonObject.put("displayMsg", contentBean.getLabel());
                    jsonObject.put("btnId", contentBean.getBtnId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                buildSendMessage(jsonObject.toString(), contentBean.getLabel(), MantisCommon.TEXT, ChatType.SDK_MSG);
            }

            @Override
            public void bigPicOnClick(String path) {
                ShowBigPicBean bigPicBean = new ShowBigPicBean(path);
                ArrayList<ShowBigPicBean> showBigPicBeans = new ArrayList<>();
                showBigPicBeans.add(bigPicBean);
                GPreviewBuilder.from(ChatActivity.this)
                        .setData(showBigPicBeans)
                        .setCurrentIndex(0)
                        .setSingleFling(true)//是否在黑屏区域点击返回
                        .setDrag(false)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();
            }

            @Override
            public void reSendMsg(ChatEntity chatEntity) {
                if (mPresenter == null) {
                    return;
                }
                upDateTitleStatus(MantisCommon.TITLE_STATUS_INPUTTING, 3000);
                mPresenter.sendMsg(chatEntity);
            }
        });

        mChatEt.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (mChatEt.isFocused() && (oldBottom - bottom) < (getWindowManager().getDefaultDisplay().getHeight() / 4)) {
                if (mChatFaceFragment.getVisibility() == View.VISIBLE) {
                    mChatFaceFragment.setVisibility(View.GONE);
                    mChatFace.setImageDrawable(getResources().getDrawable(R.drawable.chat_face));
                }
                mChatList.scrollToPosition(getListAdapter().getItemCount() - 1);

            }
        });
        //监听输入框的状态
        mChatEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //每隔200毫秒发送一次预读消息
                //排除键盘无消息(start != 0 && count != 0)
                if (System.currentTimeMillis() - recordTimestamp >= 200) {
                    recordTimestamp = System.currentTimeMillis();
                    buildSendMessage(s.toString(), s.toString(), MantisCommon.TEXT, ChatType.SDK_MSG_INPUTTING);
                }
                //记录最后一次输入时间
                inputlastTime = System.currentTimeMillis();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //监听list事件
        mChatList.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                KeyBoardUtils.closeKeyBoard(ChatActivity.this);
                mChatFaceFragment.setVisibility(View.GONE);
                mChatFace.setImageDrawable(getResources().getDrawable(R.drawable.chat_face));
            }
            return false;
        });
    }

    //初始化配置
    private void init() {
        try {
            mPresenter.initMsgListener();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.d("--------------初始化异常  mPresenter--------------   message = %s", e.getMessage(), TAG);
        }
        //初始化大图展示
        ZoomMediaLoader.getInstance().init(new BigImgLoader());
        //初始化计时器
        autoReqTimer = new Timer();
        autoReqTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (robot == null) {
                    return;
                }
                if (TextUtils.isEmpty(robot.getIsComplete()) || !robot.getIsComplete().equals("Y")) {
                    return;
                }
                if (TextUtils.isEmpty(robot.getIsRobot()) || !robot.getIsRobot().equals("Y")) {
                    return;
                }
                if (TextUtils.isEmpty(robot.getBall()) || !robot.getBall().equals("Y")) {
                    return;
                }
                if (autoCount > 30) {
                    return;
                }
                if ((System.currentTimeMillis() - inputlastTime) < 60000) {
                    return;
                }
                if ((System.currentTimeMillis() - robot.getLastTime()) < 10000) {
                    return;
                }
                robot.setLastTime(System.currentTimeMillis());
                buildSendMessage("", "", "", ChatType.SDK_SEND_AUTO_REQ);
                autoCount++;
            }
        }, 0, 1500);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.chat_face) {
            if (mChatFaceFragment.getVisibility() == View.VISIBLE) {
                mChatFace.setImageDrawable(getResources().getDrawable(R.drawable.chat_face));
                mChatFaceFragment.setVisibility(View.GONE);
                handler.postDelayed(() -> {
                    KeyBoardUtils.showInput(this, mChatEt);
                }, 50);
            } else {
                mChatFace.setImageDrawable(getResources().getDrawable(R.drawable.icon_keyboard));
                //关闭键盘
                KeyBoardUtils.closeKeyBoard(this);
                handler.postDelayed(() -> {
                    mChatFaceFragment.setVisibility(View.VISIBLE);
                }, 50);
            }
            mChatList.scrollToPosition(getListAdapter().getItemCount() - 1);
        } else if (id == R.id.chat_img) {
            List<String> list = PermissionUtils.checkPermission(this, MantisCommon.perms);
            if (list.size() <= 0) {
                selectPic();
            } else {
                PermissionUtils.requestPer(this, list, MantisCommon.REQ_CODE_PERMISSION);
            }
        } else if (id == R.id.send) {
            String str = mChatEt.getText().toString();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            for (Emoji emoji : EmojiUtil.getEmojiList()) {
                if (str.contains(emoji.getContent())) {
                    str = str.replace(emoji.getContent(), emoji.getUrl());
                }
            }
            buildSendMessage(str, mChatEt.getText().toString(), MantisCommon.TEXT, ChatType.SDK_MSG);
            mChatEt.setText("");
        } else if (id == R.id.iv_back) {
            finish();
        }
    }

    private void upDateTitleStatus(int status, int delayTime) {
        Message message = new Message();
        switch (status) {
            case MantisCommon.TITLE_STATUS_INPUTTING:
                message.obj = "对方正在输入...";
                break;
            case MantisCommon.TITLE_STATUS_GOTOTRANSFER:
                message.obj = "正在给您分配咨询师，请稍等...";
                break;
            case MantisCommon.TITLE_STATUS_TRANSFER:
                message.obj = agentName + "正在与您对话";
                break;
            default:
                message.obj = "在线客服";
        }
        message.what = UPDATE_FLAG;
        handler.sendMessageDelayed(message, delayTime);
    }

    //构建对话消息对象
    private void buildSendMessage(String content, String showContent, String type, String chatStatus) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setStatus(MantisCommon.SEND);
        chatEntity.setType(type);
        chatEntity.setShowContent(showContent);
        chatEntity.setTimestamp(System.currentTimeMillis());
        chatEntity.setContent(content);
        chatEntity.setChatStatus(chatStatus);
        sendMessage(chatEntity);
    }

    //发送对话
    private void sendMessage(ChatEntity chatEntity) {
        if (chatEntity.getChatStatus().equals(ChatType.SDK_MSG)) {
            isComplete = false;
            inputlastTime = 0;
            robot = null;
            if (welTimer != null) {
                isChat = true;
                welTimer.cancel();
            }
            upDateTitleStatus(MantisCommon.TITLE_STATUS_INPUTTING, 2000);
            addMessage(chatEntity);
        }
        mPresenter.sendMsg(chatEntity);
    }

    private void addMessage(ChatEntity chatEntity) {
        getListAdapter().addData(chatEntity);
        mChatList.scrollToPosition(getListAdapter().getItemCount() - 1);
    }

    private void setDate(List<ChatEntity> list) {
        getListAdapter().setData(list);
        mChatList.scrollToPosition(getListAdapter().getItemCount() - 1);
    }

    private ChatListAdapter getListAdapter() {
        if (adapter == null) {
            adapter = new ChatListAdapter();
            mChatList.setLayoutManager(new LinearLayoutManager(this));
            mChatList.setAdapter(adapter);
        }
        return adapter;
    }

    //调起本地图片列表
    private void selectPic() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .previewImage(true)
                .compress(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    //选择图片后回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                for (LocalMedia localMedia : list) {
                    String path = BitmapUtil.compressImage(localMedia.getCompressPath());
                    buildSendMessage(path, path, MantisCommon.IMG, ChatType.SDK_MSG);
                }
            }
        }
    }

    @Override
    public void onEmojiDelete() {
        EditTextUtils.deleteChar(mChatEt);
    }

    @Override
    public void onEmojiClick(Emoji emoji) {
        if (emoji != null) {
            int index = mChatEt.getSelectionStart();
            Editable editable = mChatEt.getEditableText();
            if (index < 0) {
                editable.append(emoji.getContent());
            } else {
                editable.insert(index, emoji.getContent());
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MantisCommon.REQ_CODE_PERMISSION) {
            if (!PermissionUtils.isSavePertmission(this)) {
                Toast.makeText(this, "请授权之后使用", Toast.LENGTH_SHORT).show();
            } else {
                showLoading();
                login();
            }
        }
    }

    @Override
    public ChatPresenter createPresenter() {
        return new ChatPresenter();
    }

    @Override
    public void onMsgSuccess() {
        if (isFirstChat) {
            firstTimer = new Timer();
            firstTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isFirstChat) {
                        closeLoading();
                        netWorkTimeOut();
                    }
                }
            }, 15000);
        }
    }

    @Override
    public void onMsgFailed(int code, String failResult, ChatEntity failChat) {
        if (failChat == null) {
            return;
        }
        closeLoading();
        //消息失败后影藏
        upDateTitleStatus(MantisCommon.TITLE_STATUS_DEFAULT, 0);
        Logger.d("SendMsg onMsgFailed  code = %s ,failResult = %s ", code, failResult, MantisCommon.TAG);
        failChat.setMsgStatus(MantisCommon.MSG_STATUS_SEND_FAIL);
        handler.postDelayed(() -> getListAdapter().upData(failChat), 500);
    }

    @Override
    public void onRecvMsg(ChatEntity msg) {
        runOnUiThread(() -> addMessage(msg));
        //系统消息只做添加
        if (msg.getStatus() == MantisCommon.SYSTEM) {
            return;
        }
        if (welTimer != null) {
            isChat = true;
            welTimer.cancel();
        }
        //收到消息后隐藏正在输入view
        upDateTitleStatus(MantisCommon.TITLE_STATUS_TRANSFER, 0);
        if (TextUtils.isEmpty(msg.getIsRobot()) || TextUtils.isEmpty(msg.getComplete())) {
            return;
        }
        Logger.d("receive  robot = %s", msg.toString(), MantisCommon.TAG);
        if (msg.getIsRobot().equals("Y") && msg.getComplete().equals("Y")) {
            isComplete = true;
        }
        if (isComplete) {
            robot = new RecordRobot();
            robot.setIsRobot(msg.getIsRobot());
            robot.setIsComplete(msg.getComplete());
            robot.setLastTime(System.currentTimeMillis());
            robot.setBall("Y");
        }
    }

    @Override
    public void onRecvWelMsg(List<ChatEntity> recChatMsg, String agentName) {
        //标识建立连接成功
        isConnectSucc = true;
        closeLoading();
        if (isFirstChat) {
            isFirstChat = false;
            firstTimer.cancel();
            if (timeOutDialog != null) {
                timeOutDialog.cancel();
            }
        }
        this.agentName = agentName;
        upDateTitleStatus(MantisCommon.TITLE_STATUS_TRANSFER, 0);
        welTimer = new Timer();
        welTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isChat) {
                    for (int i = 0; i < recChatMsg.size(); i++) {
                        ChatEntity chatEntity = recChatMsg.get(i);
                        try {
                            Thread.sleep(Integer.valueOf(chatEntity.getGap() + "000"));
                            if (!isChat) {
                                Message message = new Message();
                                message.obj = chatEntity;
                                message.arg1 = i;
                                message.what = WELCOMES_FLAG;
                                handler.sendMessage(message);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, 0);
    }


    @Override
    public void onRecvHisMsg(List<ChatEntity> msgs, String agentName) {
        //标识建立连接成功
        isConnectSucc = true;
        closeLoading();
        if (isFirstChat) {
            isFirstChat = false;
            firstTimer.cancel();
            if (timeOutDialog != null) {
                timeOutDialog.cancel();
            }
        }
        this.agentName = agentName;
        upDateTitleStatus(MantisCommon.TITLE_STATUS_TRANSFER, 0);
        setDate(msgs);
    }

    @Override
    public void onTransfer() {
        upDateTitleStatus(MantisCommon.TITLE_STATUS_GOTOTRANSFER, 0);
        buildSendMessage("", "", "", ChatType.SDK_CHAT_START);
    }

    @Override
    public void onAgentLeave() {
        //咨询师离线跳转留言界面
        startActivity(new Intent(ChatActivity.this, MsgBoardActivity.class));
    }

    @Override
    public void onChatFail() {
        if (isFirstChat) {
            isFirstChat = false;
            firstTimer.cancel();
            if (timeOutDialog != null) {
                timeOutDialog.cancel();
            }
        }
        netWorkTimeOut();
    }


    @Override
    public void onNetWork(int status) {
        if (status == MantisCommon.DISCONNECT) {
            Logger.d("NETWORK   断开连接");
        } else if (status == MantisCommon.WIFI) {
            Logger.d("NETWORK   WIFI");
        } else if (status == MantisCommon.CONNECT) {
            Logger.d("NETWORK   连接");
        }
    }

    @Override
    public void onKick() {
        runOnUiThread(() -> {
            new ChatDialog(ChatActivity.this)
                    .setTitle("提示")
                    .setMessage("您的账号在另一台设备登录，您已被踢下线！")
                    .setSingle(true)
                    .setOnClickBottomListener(new ChatDialog.OnClickBottomListener() {
                        @Override
                        public void onConfirmClick() {
                            finish();
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    }).show();
        });
    }

    //使editText点击外部时候失去焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {//点击editText控件外部
                if (isShouldHideInput(v, ev)) {//点击editText控件外部
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        assert v != null;
                        if (mChatEt != null) {
                            mChatEt.clearFocus();
                        }
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        if (autoReqTimer != null) {
            autoReqTimer.cancel();
        }
        if (welTimer != null) {
            welTimer.cancel();
        }
        //只有连接成功后页面挂掉发送离线通知
        if (isConnectSucc) {
            //发送离线通知
            buildSendMessage("", "", "", ChatType.SDK_CHAT_OFFLINE);
        }
        super.onDestroy();
    }

    private void netWorkTimeOut() {
        runOnUiThread(() -> {
            timeOutDialog = new ChatDialog(ChatActivity.this);
            timeOutDialog.setTitle("提示")
                    .setMessage("建立连接失败!")
                    .setCancelText("进入留言")
                    .setConfirmText("重新连接")
                    .setOnClickBottomListener(new ChatDialog.OnClickBottomListener() {
                        @Override
                        public void onConfirmClick() {
                            timeOutDialog.cancel();
                            showLoading();
                            buildSendMessage("", "", "", ChatType.SDK_CHAT_START);
                        }

                        @Override
                        public void onCancelClick() {
                            timeOutDialog.cancel();
                            //咨询师离线跳转留言界面
                            startActivity(new Intent(ChatActivity.this, MsgBoardActivity.class));
                            finish();
                        }
                    }).show();
        });
    }
}
