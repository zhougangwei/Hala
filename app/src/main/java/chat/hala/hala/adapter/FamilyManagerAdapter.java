package chat.hala.hala.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import chat.hala.hala.fragment.AnchorManagerFragment;
import chat.hala.hala.fragment.FamilyManagerFragment;

/**
 * Created by kiddo on 2017/11/28.
 */

public class FamilyManagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;


    public FamilyManagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        fragments.add(new FamilyManagerFragment());
        fragments.add(new AnchorManagerFragment());

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
