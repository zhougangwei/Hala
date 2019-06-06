package com.hala.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hala.fragment.HotFragment;
import com.jhjj9158.mokavideo.fragment.HomeFragment;
import com.jhjj9158.mokavideo.fragment.MsgFragment;
import com.jhjj9158.mokavideo.fragment.MyFragment;
import com.jhjj9158.mokavideo.fragment.SearchFragment;

import java.util.ArrayList;

/**
 * Created by kiddo on 2017/11/28.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;


    public TabAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        mHomeFragment = new HotFragment();
        fragments.add(mHomeFragment);
        fragments.add(new SearchFragment());
        fragments.add(new MsgFragment());
        fragments.add(new MyFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public Fragment getHomeFragment(){
        return  mHomeFragment;
    }




}
