package com.zk.drawdemo;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.drawdemo.view.BitmapView;

/**
 * author: ZK.
 * date:   On 2017/11/29.
 */
public class PageFragment extends Fragment {

    private static final String KEY_LAYOUT_ID = "key_layout_id";
    private int mLayoutId;
    private boolean mIsCreated;
    private View mView;


    public static Fragment newInstance(@LayoutRes int pageLayoutId) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LAYOUT_ID, pageLayoutId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mLayoutId = bundle.getInt(KEY_LAYOUT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(mLayoutId, container, false);
        if (mView instanceof BitmapView)
            ((BitmapView) mView).setOnClick();
        return mView;
    }
}
