package com.a17.ric.app.fragment.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a17.ric.app.R;
import com.a17.ric.app.fragment.PrimaryFragment;
import com.a17.ric.app.fragment.SentFragment;
import com.a17.ric.app.fragment.SocialFragment;

public class FragmentTab extends Fragment {

    public static final String TAG = FragmentTab.class.getSimpleName();

    private TabLayout tabLayout;
    //private HorizontalScrollView hScrollViewTab;
    private ViewPager viewPager;
    public static int TAB_COUUNT = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View view = inflater.inflate(R.layout.tab_layout, null); // View x = inflater.inflate(R.layout.tab_layout, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        //hScrollViewTab = (HorizontalScrollView) view.findViewById(R.id.hScrollViewTab);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);



        // Attach the page change listener to tab strip and **not** the view pager inside the mContext
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                System.out.print("onPageSelected");
                // hScrollViewTab.scrollTo(position, 0);
                viewPager.setCurrentItem(position, true);
                //viewPager.setCurrentItem(position);
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
                System.out.print("onPageScrolled");
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
                System.out.print("onPageScrollStateChanged");
            }
        });*/
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new ServiceJobAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(2); // Persist Three tabs
        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    class ServiceJobAdapter extends FragmentPagerAdapter {

        public ServiceJobAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragmentType with respect to Position .
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PrimaryFragment();
                case 1:
                    return new SentFragment();
                case 2:
                    return new SocialFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUUNT;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Calendar";
                case 1:
                    return "Un-signed services form";
                case 2:
                    return "Service Jobs";
            }
            return null;
        }
    }

}
