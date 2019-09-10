package chat.hala.hala.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.fragment.HotFragment;
import chat.hala.hala.fragment.NewFragment;
import chat.hala.hala.fragment.SuggestFragment;

import java.util.ArrayList;

/**
 * Created by kiddo on 2017/11/28.
 */

public class HomeAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;


    public HomeAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        if(AvchatInfo.isAnchor()){
            fragments.add(new SuggestFragment());
        }
        fragments.add(new HotFragment());
        fragments.add(new NewFragment());

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





}
