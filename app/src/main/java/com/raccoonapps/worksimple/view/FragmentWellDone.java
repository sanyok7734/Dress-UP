package com.raccoonapps.worksimple.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class FragmentWellDone extends Fragment {

    public static final String WHATSAPP = "whatsapp";
    public static final String INBOX = "inbox";
    public static final String TWITTER = "twitter";
    public static final String FACEBOOK = "facebook";

<<<<<<< HEAD

=======
>>>>>>> e7b476086e36a598ad1b863ac76ad09690ee7352
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

    private String girlImagePath;


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

    //setIcon in button
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
                String filesDir = getActivity().getApplication().getFilesDir().getPath();
                savePicture(filesDir);
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
                        shareInSocialNetwork(FACEBOOK, girlImagePath, "Hello everyone, I've created nice girl");
                        break;
                    case R.id.button_twi:
                        shareInSocialNetwork(TWITTER, girlImagePath, "Hello everyone, I've created nice girl!!!");
                        break;
                    case R.id.button_wa:
                        shareInSocialNetwork(WHATSAPP, girlImagePath, "Hello, I've created nice girl");
                        break;
                    case R.id.button_email:
                        shareInSocialNetwork(INBOX, girlImagePath, "Hello, I've created nice girl");
                        break;
                }
                break;
        }
        return true;
    }


    private void shareInSocialNetwork(String applicationName, String imagePath, String message) {
        try {
            ArrayList<Intent> intents = new ArrayList<>();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpeg");
            List<ResolveInfo> resolveInfos = getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
            if (!resolveInfos.isEmpty()) {
                for (ResolveInfo info : resolveInfos) {
                    Intent targetShare = new Intent(Intent.ACTION_SEND);
                    targetShare.setType("image/jpeg");
                    boolean isPackageNameContains = info.activityInfo.packageName.toLowerCase().contains(applicationName);
                    boolean isNameContains = info.activityInfo.name.toLowerCase().contains(applicationName);
                    if (isPackageNameContains || isNameContains) {
                        targetShare.putExtra(Intent.EXTRA_TEXT, message);
                        targetShare.putExtra(Intent.EXTRA_SUBJECT, "Pretty-girl photo");
                        Log.d("SHARING", "Image path = " + imagePath);
                        targetShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
                        targetShare.setPackage(info.activityInfo.packageName);
                        Log.d("SHARING", info.activityInfo.name);
                        intents.add(targetShare);
                        Log.d("SHARING", "Package name: " + targetShare.getPackage());
                    }
                }
                Intent chooserIntent = Intent.createChooser(
                        intents.remove(0), "Select application to share by image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        intents.toArray(new Parcelable[intents.size()]));
                startActivity(chooserIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstanceGame().post(true);
    }

    //TODO {PHOTO BUTTON, SAVING BEFORE SHARING}
    @OnTouch(R.id.button_photo)
    public boolean onTouchPhoto(View button, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                button.setAlpha(0.8f);
                break;
            case MotionEvent.ACTION_UP:
                button.setAlpha(1);
                File sdPath;
                if (!Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    sdPath = new File("Dress_UP");
                } else {
                    sdPath = Environment.getExternalStorageDirectory();
                    sdPath = new File(sdPath.getAbsolutePath() + "/" + "Dress_UP");
                    // create directory
                    sdPath.mkdirs();
                }
                savePicture(sdPath.getPath());
                splash();
                break;
        }
        return true;
    }

    private void savePicture(String rootPath) {
        Bitmap bitmap = getBitmap();
        FileOutputStream fos = null;
        try {
            String fullPath = rootPath + "/" + "Dress_UP_"
                    + System.currentTimeMillis() + ".jpg";
            fos = new FileOutputStream(fullPath);
            girlImagePath = fullPath;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
        }
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
