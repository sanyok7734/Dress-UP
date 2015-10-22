package com.raccoonapps.worksimple.view;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.eventbus.BusProvider;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class FragmentStart extends Fragment {

    private static final String FONT_PATH = "fonts/PFArmonia-Bold.ttf";
    private static final String FONT_PATH_LOGO = "fonts/PFArmonia-Reg.ttf";

    private static final Uri ADDRES_BANNER = Uri.parse("https://quickappninja.com/");
    private static final Uri MORE = Uri.parse("https://quickappninja.com/showcase/");

    @Bind(R.id.button_start) TextView buttonStart;
    @Bind(R.id.image_start) ImageView imageStart;

    @Bind(R.id.button_more) TextView buttonMore;
    @Bind(R.id.image_more) ImageView imageMore;

    @Bind(R.id.button_help) TextView buttonHelp;
    @Bind(R.id.image_help) ImageView imageHelp;

    @Bind(R.id.text_logo) TextView textLogo;
    @Bind(R.id.banner) LinearLayout banner;


    @Bind(R.id.frame_start) FrameLayout frameStart;
    @Bind(R.id.frame_more) FrameLayout frameMore;
    @Bind(R.id.frame_help) FrameLayout frameHelp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_start_game, container, false);
        ButterKnife.bind(this, view);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), FONT_PATH);
        Typeface typefaceLogo = Typeface.createFromAsset(getActivity().getAssets(), FONT_PATH_LOGO);

        textLogo.setTypeface(typefaceLogo);

        buttonHelp.setTypeface(typeface);
        buttonMore.setTypeface(typeface);
        buttonStart.setTypeface(typeface);

        //TODO VISIBLE/GONE BANNER
        banner.setVisibility(View.VISIBLE);

        final Intent openLink = new Intent(Intent.ACTION_VIEW, ADDRES_BANNER);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openLink);
            }
        });



        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @OnTouch({ R.id.button_start, R.id.button_more, R.id.button_help })
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
                        BusProvider.getInstance().post(new FragmentGame());
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
                final Intent openLink = new Intent(Intent.ACTION_VIEW, ADDRES_BANNER);
                startActivity(openLink);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
