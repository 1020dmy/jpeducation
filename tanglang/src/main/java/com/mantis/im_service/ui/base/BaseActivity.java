package com.mantis.im_service.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mantis.im_service.R;
import com.mantis.im_service.ui.dialog.LoadingView;

import java.util.List;

/**
 * Created by Mr.huang on 2020-1-13
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView<P> {
    public LoadingView loading;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new LoadingView(this, R.style.CustomDialog);
        mPresenter = createPresenter();

        if (mPresenter == null) {
            throw new NullPointerException(" createPresenter 必须返回一个有效的 Presenter ");
        }
        mPresenter.attachView(this);

    }


    protected Fragment addFragment(FragmentManager manager, Class<? extends Fragment> aClass, int containerId, Bundle args) {
        String tag = aClass.getName();
        /**
         * 先查询fragmentManager 所在的activitiy 中是否已经添加了这个fragment
         *  第一步 先从一个mAdded 的一个ArrayList遍历查找，如果找不到再从 一个 叫 mActive 的 SparseArray的一个map
         *  里面查找。
         *
         *
         * 注意：
         * 1.一个 fragment 被 remove 掉后，只会从 mAdded 里面删除，不会从 mActive 里面删除，只有当这个fragment 所在的 transaction
         * 从回退栈里面移除后才会 从mActive 删除
         * 2. 当我们add 一个fragment时 会把我们的fragment 添加到 mAdded 里面，不会添加到 mActive。
         * 3. 只有当我们把 transaction 添加到回退栈的时候，才会把我们的 fragment 添加到 mActive 里面。
         *
         *
         * 所以我们通过 findFragmentByTag 方法查找出来的 fragment 不一定是被添加到我们的 activity 中。
         */

        Fragment fragment = manager.findFragmentByTag(tag);


        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if (fragment == null) {// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例

//                BaseFragment baseFragment = (BaseFragment) fragment; // 强转成我们base fragment
//
//                // 设置 fragment 进入，退出， 弹进，弹出的动画
//                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

                transaction.add(containerId, fragment, tag);

//                if (baseFragment.isNeedToAddBackStack()) { // 判断是否需要加入回退栈
//                    transaction.addToBackStack(tag); // 加入回退栈时制定一个tag，以便在找到指定的事务
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }

        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
            return fragment;

        }

        return null;
    }

    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     *
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            loading.dismiss();
            if (mPresenter != null) {
                mPresenter.detachView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoading() {
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    public void closeLoading() {
        loading.dismiss();
    }
}
