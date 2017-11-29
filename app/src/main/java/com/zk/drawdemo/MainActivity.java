package com.zk.drawdemo;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<PageModel> mPageModels = new ArrayList<>();

    {
        mPageModels.add(new PageModel("color", R.layout.view_color));
        mPageModels.add(new PageModel("circle", R.layout.view_circle));
        mPageModels.add(new PageModel("rect", R.layout.view_rect));
        mPageModels.add(new PageModel("point", R.layout.view_point));
        mPageModels.add(new PageModel("oval", R.layout.view_oval));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = mPageModels.get(position);
                return PageFragment.newInstance(pageModel.layoutId);
            }

            @Override
            public int getCount() {
                return mPageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mPageModels.get(position).title;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private class PageModel {

        public String title;
        public int layoutId;

        public PageModel(String title, int layoutId) {
            this.title = title;
            this.layoutId = layoutId;
        }
    }


}