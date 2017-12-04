package com.a17.ric.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a17.ric.app.R;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SentFragment extends Fragment {

    public static final String TAG = SentFragment.class.getSimpleName();
    private static final String TITLE = "TITLE";

    public static SentFragment newInstance(String title) {
        SentFragment frag = new SentFragment();
        Bundle args = new Bundle();

        args.putString(TITLE, title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sent_layout, container, false);
    }

}
