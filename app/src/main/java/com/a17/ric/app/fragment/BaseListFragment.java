package com.a17.ric.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a17.ric.app.R;
import com.a17.ric.app.common.view.SnackBarNotificationUtil;

public abstract class BaseListFragment extends Fragment {

    private static final String TAG = BaseListFragment.class.getSimpleName();

    /* THESE View are defined inside the Fragments MyList Class*/
    private TextView textViewListResult;
    private Button buttonRetry;
    private ImageView imageNoConnection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewListResult = (TextView) view.findViewById(R.id.textViewListResult);
        buttonRetry = (Button) view.findViewById(R.id.buttonRetry);
        imageNoConnection = (ImageView) view.findViewById(R.id.imageNoConnection);

        setNoConnectionStatus(View.GONE);

        setButtonRetryListener(true);
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract int getFragmentLayout();
    protected abstract void setResultListVisibility(int state);
    protected abstract void reRenderResultList();

    // TODO: Make this 2 Private?
    public void setTextViewListResult(String result) {
        textViewListResult.setText(result);
    }
    public void setTextViewListResult(int stringID) {
        textViewListResult.setText(stringID);
    }

    private void setNoConnectionStatus(int state) { //
        textViewListResult.setVisibility(state);
        buttonRetry.setVisibility(state);
        imageNoConnection.setVisibility(state);
        setButtonRetryListener(true);
    }

    public void noResultTryAgain() {
        setResultListVisibility(View.GONE);
        setTextViewListResult("Try again later.");
        setNoConnectionStatus(View.VISIBLE);
    }

    public void noResponse(String message) {
        setResultListVisibility(View.GONE);
        setTextViewListResult(message);
        setNoConnectionStatus(View.VISIBLE);
        imageNoConnection.setVisibility(View.GONE);
    }

    // Just to Show the list if there's a result, this can be removed and instead apply on the Fragment
    public void setResultListVisibility() {
        setResultListVisibility(View.VISIBLE);
        setNoConnectionStatus(View.GONE);
    }

    // To avoid multiple click on the Retry button
    // Disable this on reRenderResultList(), then call this as TRUE after Request
    private void setButtonRetryListener(boolean state) {
        if (state)
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retryConnection();
                }
            });
        else
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Disable Click
                    Log.d(TAG, "Disabled...");
                }
            });
    }

    public void retryConnection() {
        if (/*NetworkUtil.isConnected(getActivity())*/ false) {
            setButtonRetryListener(false);
            //setResultListVisibility(View.VISIBLE);
            setNoConnectionStatus(View.GONE);
            reRenderResultList();
        } else {
            noInternetSnackBar();
        }
    }

    public void noInternetSnackBar() {
        setResultListVisibility(View.GONE);
        setTextViewListResult(R.string.noInternetPrompt);
        setNoConnectionStatus(View.VISIBLE);
        if (isAdded() && getActivity() != null) {
            SnackBarNotificationUtil
                    .setSnackBar(getActivity().findViewById(android.R.id.content),
                            getResources().getString(R.string.noInternetConnection))
                    .setColor(getResources().getColor(R.color.colorPrimary1))
                    .show(this);
        }
    }

    // TODO: progress dialog on list NOT the whole VIEW
}
