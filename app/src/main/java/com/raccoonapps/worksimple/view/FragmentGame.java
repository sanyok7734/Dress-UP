package com.raccoonapps.worksimple.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.adapters.AdapterAccessory;
import com.raccoonapps.worksimple.adapters.AdapterCategory;
import com.raccoonapps.worksimple.components.AccessoryWrapper;
import com.raccoonapps.worksimple.components.CategoryWrapper;
import com.raccoonapps.worksimple.controller.AccessoryController;
import com.raccoonapps.worksimple.controller.ElementsCoordinator;
import com.raccoonapps.worksimple.controller.Squeezing;
import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.Category;
import com.raccoonapps.worksimple.music.MainPlayer;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.BUTTON;
import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.IMAGE;
import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.TRACK;
import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.getLoader;

public class FragmentGame extends Fragment {

    private AdapterCategory adapterCategory;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.LayoutManager layoutAccessory;

    private GestureDetectorCompat detector;

    private Bitmap bitmap = null;

    private List<CategoryWrapper> categoryWrappers = new ArrayList<>();

    private CategoryWrapper categoryWrapper;

    private boolean scroll = true;
    private boolean isResumed = false;

    @Bind(R.id.list_category)
    RecyclerView listCategory;
    @Bind(R.id.additional_panel)
    RecyclerView additionalPanel;
    @Bind(R.id.content_girl)
    FrameLayout contentGirl;
    @Bind(R.id.girl)
    ImageView girlImage;
    @Bind(R.id.scroll_category)
    ImageView scrollCategory;

    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.content_game)
    RelativeLayout contentGame;

    //button game
    @Bind(R.id.button_back_background)
    ImageView buttonBackBackground;

    @Bind(R.id.button_sound_background)
    ImageView buttonSoundBackground;

    @Bind(R.id.button_next_background)
    ImageView buttonNextBackground;

    private int visibleCategoriesCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_play_game, container, false);
        ButterKnife.bind(this, view);
        BusProvider.getInstanceGame().register(this);
        setIcon();

        MainPlayer.getInstance(getActivity()).resetPlayer();
        MainPlayer player = MainPlayer.getInstance(getActivity());
        player.play(getLoader(getActivity()).getTrackIdByName(TRACK.MAIN));

        ViewGroup.LayoutParams layoutParam = girlImage.getLayoutParams();
        layoutParam.height = Squeezing.occupyHeightGirl();
        layoutParam.width = Squeezing.occupyWidthGirl();
        girlImage.setLayoutParams(layoutParam);
        girlImage.setImageDrawable(Squeezing.getImageGirl());

        //TODO coordination girl
        //coordination girl
        girlImage.setTranslationX(ElementsCoordinator.setCoordinatorGirlX(Squeezing.occupyWidthGirl(), 50));
        girlImage.setTranslationY(ElementsCoordinator.setCoordinatorGirlY(Squeezing.occupyHeightGirl(), 50));

        categoryWrappers = getCategoryWrappers();


        layoutAccessory = new LinearLayoutManager(getActivity());
        additionalPanel.setLayoutManager(layoutAccessory);

        adapterCategory = new AdapterCategory(categoryWrappers, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        listCategory.setLayoutManager(mLayoutManager);
        listCategory.setAdapter(adapterCategory);
        listCategory.getItemAnimator().setChangeDuration(0);

        listCategory.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    scrollCategory.setImageResource(setIcon(BUTTON.SCROLL));
                }
                int allItemCategory = 0;
                allItemCategory = getLoader(getActivity()).getAllCategories().size();
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == allItemCategory - 1) {
                    scrollCategory.setImageResource(setIcon(BUTTON.SCROLL_PRESSED));
                }
            }
        });

        ViewGroup.LayoutParams layoutParamList = listCategory.getLayoutParams();
        layoutParamList.height = getCategoriesListHeight();
        listCategory.setLayoutParams(layoutParamList);

        return view;
    }

    private void setIcon() {
        scrollCategory.setImageResource(setIcon(BUTTON.SCROLL));
        buttonNextBackground.setImageResource(setIcon(BUTTON.FORWARD));
        buttonSoundBackground.setImageResource(setIcon(BUTTON.MUSIC));
        buttonBackBackground.setImageResource(setIcon(BUTTON.BACK));
        background.setImageResource(getLoader(getActivity()).getImageIdByName(IMAGE.GAME_BG));
    }

    private int setIcon(BUTTON button) {
        return getLoader(getActivity()).getButtonIdByName(button);
    }

    @OnTouch(R.id.content_girl)
    public boolean touchContentGirl(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                ArrayList<AccessoryWrapper> deleteAccessories = new ArrayList<>();

                int pressX = (int) motionEvent.getX();
                int pressY = (int) motionEvent.getY();

                for (int i = 0; i < AccessoryController.size(); i++) {
                    int fromX = (int) AccessoryController.getAccessoryWrapper(i).getFromX();
                    int toX = (int) AccessoryController.getAccessoryWrapper(i).getToX();
                    int fromY = (int) AccessoryController.getAccessoryWrapper(i).getFromY();
                    int toY = (int) AccessoryController.getAccessoryWrapper(i).getToY();

                    if (AccessoryController.contain(pressX, fromX, toX)) {
                        if (AccessoryController.contain(pressY, fromY, toY)) {
                            AccessoryController.getAccessoryWrapper(i).setX(pressX - fromX);
                            AccessoryController.getAccessoryWrapper(i).setY(pressY - fromY);
                            //sorry but now night and I can not think
                            if (AccessoryController.getAccessoryWrapper(i).isRemove()) {
                                deleteAccessories.add(AccessoryController.getAccessoryWrapper(i));
                            }
                        }
                    }
                }

                Collections.sort(deleteAccessories);
                if (deleteAccessories.size() != 0) {
                    AccessoryWrapper deleteAccessory = deleteAccessories.get(deleteAccessories.size() - 1);
                    deleteAccessory.getCategoryWrapper().deleteAccesorry(deleteAccessory.getTag());
                }
                break;
        }
        return true;
    }


    private int getCategoriesListHeight() {
        int allItemCategory = 7;
        double listHeight;
        double nBtnSize = (int) getResources().getDimension(R.dimen.buttonNextWidthHeight);
        double mTopBtnSize = (int) getResources().getDimension(R.dimen.buttonNextTop);
        double mBottomBtnSize = (int) getResources().getDimension(R.dimen.buttonNextTop);
        double scrBtnSize = (int) getResources().getDimension(R.dimen.scrollButtonSize);
        double categoryItemHeight = (int) getResources().getDimension(R.dimen.categoryWidthHeight);

        double sumSizeButton = nBtnSize + mTopBtnSize + mBottomBtnSize + scrBtnSize;
        double categoryHeight = MainActivity.screenHeight - sumSizeButton;

        visibleCategoriesCount = (int) (categoryHeight / categoryItemHeight);
        allItemCategory = getLoader(getActivity()).getAllCategories().size();
        if (allItemCategory <= visibleCategoriesCount) {
            listHeight = allItemCategory * categoryItemHeight;
            listHeight = (listHeight + ((1 * categoryHeight) / 100));
            scrollCategory.setVisibility(View.GONE);
        } else {
            listHeight = visibleCategoriesCount * categoryItemHeight;
            scrollCategory.setVisibility(View.VISIBLE);
        }
        return (int) (listHeight);
    }

    @Subscribe
    public void panelAccessoryShow(CategoryWrapper categoryWrapper) {
        this.categoryWrapper = categoryWrapper;
        additionalPanel.setAdapter(new AdapterAccessory(categoryWrapper.getAccessories(), getActivity()));

        ObjectAnimator animXPanel = ObjectAnimator.ofFloat(additionalPanel, "x", contentGirl.getWidth() - additionalPanel.getWidth());
        ObjectAnimator animXScreen = ObjectAnimator.ofFloat(contentGirl, "x", -additionalPanel.getWidth());
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(700);
        animSet.playTogether(animXPanel, animXScreen);
        animSet.start();
        additionalPanel.setTag("open");
    }

    //close panel accessories
    @Subscribe
    public void panelAccessoryHide(String panel) {
        if (panel.equals("Panel")) {
            ObjectAnimator animXPanel = ObjectAnimator.ofFloat(additionalPanel, "x", contentGirl.getWidth());
            ObjectAnimator animXScreen = ObjectAnimator.ofFloat(contentGirl, "x", 0);
            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(700);
            animSet.playTogether(animXPanel, animXScreen);
            animSet.start();
            additionalPanel.setTag("close");
        }
    }

    // pressing on accessory for him placement
    @Subscribe
    public void accessoryCodrinate(Integer position) {
        List<Accessory> accessories = categoryWrapper.getAccessories();
        int tag = accessories.get(position).getImage();
        // получаем картинку в Bitmap и передаем координаты для корденирования элемента
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(accessories.get(position).getImage());
        double X = accessories.get(position).getCoordinates().getX();
        double Y = accessories.get(position).getCoordinates().getY();
        categoryWrapper.setCoordinateImage(tag, drawable, X, Y);
    }


    @Override
    public void onDestroy() {
        super.onPause();
        BusProvider.getInstanceGame().unregister(this);
    }


    @OnTouch({R.id.button_back, R.id.button_next, R.id.button_sound})
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // press
                v.setAlpha(0.8f);
                break;
            case MotionEvent.ACTION_UP: // release
                v.setAlpha(1);
                switch (v.getId()) {
                    case R.id.button_back:
                        MainActivity.onClickStart = true;
                        MainActivity.fragmentManager.popBackStack();
                        break;
                    case R.id.button_sound:
                        boolean isMuted = MainPlayer.getInstance(getActivity()).isMuted();
                        v.setTag(isMuted ? "stop" : "play");
                        if (v.getTag().equals("play")) {
                            MainPlayer.getInstance(getActivity()).mute();
                            v.setTag("stop");
                        } else {
                            MainPlayer.getInstance(getActivity()).unmute();
                            v.setTag("play");
                        }
                        break;
                    case R.id.button_next:
                        if (MainActivity.onClickWellDone) {
                            MainActivity.onClickWellDone = false;
                            MainPlayer.getInstance(getActivity()).pause();

                            background.setImageResource(getLoader(getActivity()).getImageIdByName(IMAGE.WELLDONE_BG));
                            contentGame.setVisibility(View.INVISIBLE);
                            if (additionalPanel.getTag().equals("open")) {
                                contentGirl.setTranslationX(0);
                                additionalPanel.setVisibility(View.INVISIBLE);
                            }

                            View v1 = contentGirl.getRootView();
                            v1.setDrawingCacheEnabled(true);
                            bitmap = v1.getDrawingCache();

                            background.setImageResource(getLoader(getActivity()).getImageIdByName(IMAGE.GAME_BG));
                            contentGame.setVisibility(View.VISIBLE);
                            if (additionalPanel.getTag().equals("open")) {
                                contentGirl.setTranslationX(-additionalPanel.getWidth());
                                additionalPanel.setVisibility(View.VISIBLE);
                            }

                            Bundle bundle = new Bundle();
                            bundle.putParcelable("well_done", bitmap);
                            FragmentWellDone fragmentWellDone = new FragmentWellDone();
                            fragmentWellDone.setArguments(bundle);
                            BusProvider.getInstanceMain().post(fragmentWellDone);
                            break;
                        }
                }
                break;
        }
        return true;
    }

    @Subscribe
    public void refreshScreen(String refresh) {
        if (refresh.equals("RefreshGameScreen")) {
            panelAccessoryHide("Panel");
            adapterCategory.resetCategoryIcon();
            int itemHeight = listCategory.getChildAt(0).getHeight();
            int countCategory = categoryWrappers.size() - visibleCategoriesCount;
            listCategory.smoothScrollBy(itemHeight, -itemHeight * countCategory - 10);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isResumed) {
            MainPlayer.getInstance(getActivity()).resume();
            isResumed = false;
        }
        MainActivity.onClickWellDone = true;
    }

    @Subscribe
    public void subscribeSomeStuff(Boolean object) {
        if (object)
            MainPlayer.getInstance(getActivity()).pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        isResumed = true;
        MainPlayer.getInstance(getActivity()).pause();
    }

    private List<CategoryWrapper> getCategoryWrappers() {
        List<CategoryWrapper> categoryWrappers = new ArrayList<>();

        List<Category> categories = null;
        categories = getLoader(getActivity()).getAllCategories();

        for (Category category : categories) {
            categoryWrappers.add(new CategoryWrapper(category, getActivity(), contentGirl));
        }
        return categoryWrappers;
    }
}