package com.raccoonapps.worksimple.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.music.MainPlayer;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class FragmentWellDone extends Fragment {

    public static final String WHATSAPP = "whatsapp";
    public static final String TWITTER = "twitter";
    public static final String FACEBOOK = "facebook";
    private final File externalStorageDirectory = Environment.getExternalStorageDirectory();

    @Bind(R.id.well_done_girl)
    ImageView wellDoneGirl;
    @Bind(R.id.splash)
    View splash;
    @Bind(R.id.root)
    FrameLayout root;
    @Bind(R.id.buttons)
    RelativeLayout buttons;
    @Bind(R.id.banner_well_done)
    FrameLayout banner;

    @Bind(R.id.button_restart_background)
    ImageView buttonRestartBackground;
    @Bind(R.id.button_restart_icon)
    ImageView buttonRestartIcon;

    @Bind(R.id.button_back_background)
    ImageView buttonBackBackground;
    @Bind(R.id.button_back_icon)
    ImageView buttonBackIcon;

    @Bind(R.id.background_email)
    ImageView backgroundEmail;
    @Bind(R.id.icon_email)
    ImageView iconEmail;

    @Bind(R.id.background_wa)
    ImageView backgroundWa;
    @Bind(R.id.icon_wa)
    ImageView iconWa;

    @Bind(R.id.background_twi)
    ImageView backgroundTwi;
    @Bind(R.id.icon_twi)
    ImageView iconTwi;

    @Bind(R.id.background_fb)
    ImageView backgroundFb;
    @Bind(R.id.icon_fb)
    ImageView iconFb;

    @Bind(R.id.background_photo)
    ImageView backgroundPhoto;
    @Bind(R.id.icon_photo)
    ImageView iconPhoto;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_well_done, container, false);
        ButterKnife.bind(this, view);
        setIcon();

        Bundle bundle = this.getArguments();
        Bitmap bitmap = bundle.getParcelable("well_done");
        BitmapDrawable wellDoneGirlImage = new BitmapDrawable(getResources(), bitmap);

        wellDoneGirl.setImageDrawable(wellDoneGirlImage);
        return view;
    }

    private void setIcon() {
        buttonRestartBackground.setImageResource(R.drawable.btn_circle);
        buttonRestartIcon.setImageResource(R.drawable.refresh);
        buttonBackBackground.setImageResource(R.drawable.btn_circle);
        buttonBackIcon.setImageResource(R.drawable.back);
        backgroundEmail.setImageResource(R.drawable.email);
        iconEmail.setImageResource(R.drawable.mail);
        backgroundWa.setImageResource(R.drawable.sharing_wa);
        iconWa.setImageResource(R.drawable.whatsapp);
        backgroundTwi.setImageResource(R.drawable.sharing_twi);
        iconTwi.setImageResource(R.drawable.twitter);
        backgroundFb.setImageResource(R.drawable.sharing_fb);
        iconFb.setImageResource(R.drawable.facebook);
        backgroundPhoto.setImageResource(R.drawable.photo_btn);
        iconPhoto.setImageResource(R.drawable.photo);
    }

    @OnTouch({R.id.button_restart, R.id.button_back,
            R.id.button_fb, R.id.button_twi, R.id.button_wa, R.id.button_email})
    public boolean onTouch(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setAlpha(0.8f);
                break;

            case MotionEvent.ACTION_UP:
                button.setAlpha(1);
                switch (button.getId()) {
                    case R.id.button_back:
                        MainPlayer.getInstance(getActivity()).resume();
                        MainActivity.fragmentManager.popBackStack();
                        break;
                    case R.id.button_restart:
                        MainPlayer.getInstance(getActivity()).resetPlayer();
                        for (int i = 0; i < MainActivity.fragmentManager.getBackStackEntryCount(); ++i) {
                            MainActivity.fragmentManager.popBackStack();
                        }
                        BusProvider.getInstanceMain().post(new FragmentGame());
                        break;
                    case R.id.button_fb:
                        break;
                    case R.id.button_twi:
                        break;
                    case R.id.button_wa:
                        break;
                    case R.id.button_email:
                        String externalStorageDirectory = Environment.getExternalStorageDirectory() + "/DRESS_UP";
                        if (!new File(externalStorageDirectory).exists())
                            new File(externalStorageDirectory).mkdirs();
                        String cachedPicture = savePicture(externalStorageDirectory);
                        shareViaEmail(cachedPicture);
                        break;
                }
                break;
        }
        return true;
    }

    private void shareViaEmail(String picturePath) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);
        Log.d("SHARING", "Image path = " + picturePath);
        emailIntent.setType("application/image");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "From My App");
        File image = new File(picturePath);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
        emailIntent.setData(Uri.parse("mailto:"));
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstanceGame().post(true);
    }

    //TODO {PHOTO BUTTON}
    @OnTouch(R.id.button_photo)
    public boolean onTouchPhoto(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setAlpha(0.8f);
                break;
            case MotionEvent.ACTION_UP:
                button.setAlpha(1);
                if (externalStorageDirectory.exists())
                    savePicture(externalStorageDirectory.toString());
                else
                    savePicture(getActivity().getApplication().getFilesDir().getAbsolutePath());
                splash();
                break;
        }
        return true;
    }

    private String savePicture(String rootPath) {
        Bitmap bitmap = getBitmap();
        FileOutputStream fos;
        String fullPath = null;
        try {
            fullPath = rootPath + "/" + "Dress_UP_"
                    + System.currentTimeMillis() + ".jpg";
            fos = new FileOutputStream(fullPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
        }
        return fullPath == null ? "" : fullPath;
    }

    private Bitmap getBitmap() {
        buttons.setVisibility(View.INVISIBLE);
        banner.setVisibility(View.VISIBLE);
        wellDoneGirl.setVisibility(View.VISIBLE);

        View rootView = (View) root;
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = rootView.getDrawingCache();

        buttons.setVisibility(View.VISIBLE);
        banner.setVisibility(View.INVISIBLE);
        return bitmap;
    }


    private void splash() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(splash, "Alpha", 0, 1);
        animator.setDuration(100).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                splash.setAlpha(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }

}
