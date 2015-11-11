package com.raccoonapps.worksimple.view;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader;
import com.raccoonapps.worksimple.eventbus.BusProvider;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.getLoader;

public class FragmentStart extends Fragment {

    private static final Uri MORE = Uri.parse("https://quickappninja.com/showcase/");

    private View view;

    @Bind(R.id.button_start)
    TextView buttonStart;
    @Bind(R.id.image_start)
    ImageView imageStart;

    @Bind(R.id.button_more)
    TextView buttonMore;
    @Bind(R.id.image_more)
    ImageView imageMore;

    @Bind(R.id.button_help)
    TextView buttonHelp;
    @Bind(R.id.image_help)
    ImageView imageHelp;
    @Bind(R.id.frame_start)
    RelativeLayout frameStart;
    @Bind(R.id.frame_more)
    FrameLayout frameMore;
    @Bind(R.id.frame_help)
    FrameLayout frameHelp;

    @Bind(R.id.background)
    LinearLayout background;

    private static TextView textLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_start_game, container, false);
        ButterKnife.bind(this, view);
        setIcon();
        return view;
    }

    private void setIcon() {
        imageHelp.setImageResource(setIcon(ApplicationPropertiesLoader.BUTTON.FAQ));
        imageMore.setImageResource(setIcon(ApplicationPropertiesLoader.BUTTON.MORE));
        imageStart.setImageResource(setIcon(ApplicationPropertiesLoader.BUTTON.START));
        background.setBackgroundResource(getLoader(getActivity()).getImageIdByName(ApplicationPropertiesLoader.IMAGE.SPLASH));
    }

    private int setIcon(ApplicationPropertiesLoader.BUTTON button) {
        return getLoader(getActivity()).getButtonIdByName(button);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.onClickStart = true;
        MainActivity.onClickWellDone = true;
        boolean whiteLabel = Boolean.parseBoolean(getActivity().getResources().getString(R.string.white_label));
        if (whiteLabel) {
            MainActivity.preferenceBanner(view, getActivity(), View.VISIBLE);
        } else {
            MainActivity.preferenceBanner(view, getActivity(), View.GONE);
        }

    }

    @OnTouch({R.id.button_start, R.id.button_more, R.id.button_help})
    public boolean onTouch(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (button.getId()) {
                    case R.id.button_start:
                        frameStart.setAlpha(0.89f);
                        break;
                    case R.id.button_more:
                        frameMore.setAlpha(0.89f);
                        break;
                    case R.id.button_help:
                        frameHelp.setAlpha(0.8f);
                        break;
                }
                break;

            case MotionEvent.ACTION_UP:
                switch (button.getId()) {
                    case R.id.button_start:
                        frameStart.setAlpha(1);
                        if (MainActivity.onClickStart) {
                            MainActivity.onClickStart = false;
                            BusProvider.getInstanceMain().post(new FragmentGame());
                        }
                        break;
                    case R.id.button_more:
                        frameMore.setAlpha(1);
                        final Intent openLinkMore = new Intent(Intent.ACTION_VIEW, MORE);
                        startActivity(openLinkMore);
                        break;
                    case R.id.button_help:
                        frameHelp.setAlpha(1);
                        final SpannableString string = new SpannableString("This amazing game was created with QuickApp Ninja builder. Easy build free-to-play games, upload it to Android market and make money from ads. You don't need any special skills and no coding is required. For more information please visit => http://quickappninja.com");
                        showAlterDialog(string);
                        break;
                }
                break;
        }
        return true;
    }

    private void showAlterDialog(SpannableString s) {
        Linkify.addLinks(s, Linkify.ALL);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(s);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent openLink = new Intent(Intent.ACTION_VIEW, MainActivity.ADDRESS_BANNER);
                startActivity(openLink);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
