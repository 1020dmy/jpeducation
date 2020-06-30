package com.jianpei.jpeducation.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.activitys.order.OrderConfirmActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WX_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            startActivity(new Intent(this, OrderConfirmActivity.class).putExtra("resultCode", resp.errCode));
            finish();
        }

    }
}