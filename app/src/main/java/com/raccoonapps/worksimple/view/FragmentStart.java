package com.raccoonapps.worksimple.view;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.model.CoordinatorElements;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentStart extends Fragment {

    private static final String FONT_PATH = "fonts/PFArmonia-Bold.ttf";

    @Bind(R.id.button_start) TextView buttonStart;
    @Bind(R.id.image_start) ImageView imageStart;

    @Bind(R.id.button_more) TextView buttonMore;
    @Bind(R.id.image_more) ImageView imageMore;

    @Bind(R.id.button_help) TextView buttonHelp;
    @Bind(R.id.image_help) ImageView imageHelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_start_game, container, false);
        ButterKnife.bind(this, view);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), FONT_PATH);

        buttonHelp.setTypeface(typeface);
        buttonMore.setTypeface(typeface);
        buttonStart.setTypeface(typeface);

        //TODO setImage on button

        return view;
    }

    @OnClick({ R.id.button_start, R.id.button_more, R.id.button_help })
    public void onClick(TextView button){
        switch (button.getId()) {
            case R.id.button_start:
                BusProvider.getInstance().post(new FragmentGame());
                Log.d(CoordinatorElements.TAG, "StartStartEVENT");
                break;
            case R.id.button_more:
                Snackbar.make(getView(), "More", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.button_help:
                Snackbar.make(getView(), "Help", Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}
