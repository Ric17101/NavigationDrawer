package com.a17.ric.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a17.ric.app.R;


public class UnderMaintenanceFragment extends Fragment {

    private static final String TAG = UnderMaintenanceFragment.class.getSimpleName();
    private TextView textViewFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.under_maintenance_layout, container, false);
        updateUI(view);
        return view;
    }

    private void updateUI(View view) {
        String title = String.format("Under Maintenance");
        textViewFragment = (TextView) view.findViewById(R.id.textViewFragment);
        textViewFragment.setText(title);

    }
    /**
     * These Two Lines should be included on every Fragment to maintain the state and do not load again
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        System.out.println("SocialFragment: I'm on the onCreate");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("SocialFragment: I'm on the onSaveInstanceState");
    }
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        System.out.println("SocialFragment: I'm on the onActivityCreated");
    }

}
