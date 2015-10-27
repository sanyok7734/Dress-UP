package com.raccoonapps.worksimple.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.raccoonapps.worksimple.adapters.AdapterAccessory;
import com.raccoonapps.worksimple.adapters.AdapterCategory;
import com.raccoonapps.worksimple.components.CategoryWrapper;
import com.raccoonapps.worksimple.eventbus.BusProvider;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.ApplicationPropertiesLoader;
import com.raccoonapps.worksimple.model.Category;
import com.raccoonapps.worksimple.model.CoordinatorElements;
import com.raccoonapps.worksimple.model.Squeezing;
import com.raccoonapps.worksimple.music.MainPlayer;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class FragmentGame extends Fragment {

    private AdapterCategory adapterCategory;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.LayoutManager layoutAccessory;

    private Bitmap bitmap = null;

    private List<CategoryWrapper> categoryWrappers = new ArrayList<>();

    private CategoryWrapper categoryWrapper;

    private boolean scroll = true;

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
    @Bind(R.id.button_back_image)
    ImageView buttonBackImage;

    @Bind(R.id.button_sound_background)
    ImageView buttonSoundBackground;
    @Bind(R.id.button_sound_image)
    ImageView buttonSoundImage;

    @Bind(R.id.button_next_background)
    ImageView buttonNextBackground;
    @Bind(R.id.button_next_image)
    ImageView buttonNextImage;
    private int visibleCategoriesCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_play_game, container, false);
        ButterKnife.bind(this, view);
        BusProvider.getInstanceGame().register(this);

        MainPlayer.getInstance(getActivity()).resetPlayer();
        MainPlayer player = MainPlayer.getInstance(getActivity());
        try {
            player.play(ApplicationPropertiesLoader.getLoader(getActivity()).getTrackIdByName(ApplicationPropertiesLoader.TRACK.MAIN));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewGroup.LayoutParams layoutParam = girlImage.getLayoutParams();
        layoutParam.height = Squeezing.occupyHeightGirl();
        layoutParam.width = Squeezing.occupyWidthGirl();
        girlImage.setLayoutParams(layoutParam);
        girlImage.setImageDrawable(Squeezing.getImageGirl());
        //coordination girl
        girlImage.setTranslationX(CoordinatorElements.setCoordinatorGirlX(Squeezing.occupyWidthGirl(), 50));
        girlImage.setTranslationY(CoordinatorElements.setCoordinatorGirlY(Squeezing.occupyHeightGirl(), 50));

        categoryWrappers = getCategoryWrappers();


        layoutAccessory = new LinearLayoutManager(getActivity());
        additionalPanel.setLayoutManager(layoutAccessory);

        adapterCategory = new AdapterCategory(categoryWrappers);
        mLayoutManager = new LinearLayoutManager(getActivity());
        listCategory.setLayoutManager(mLayoutManager);
        listCategory.setAdapter(adapterCategory);
        listCategory.getItemAnimator().setChangeDuration(0);

        listCategory.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mLayoutManager.findFirstCompletelyVisibleItemPosition()==0) {
                    Log.d("SCROLL", "TOP");
                    scrollCategory.setImageResource(R.drawable.scroll_ic);
                }
                int allItemCategory = 0;
                //TODO CHANGE IDEOTE
                try {
                    allItemCategory = ApplicationPropertiesLoader.getLoader(getActivity()).getAllCategories().size();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == allItemCategory - 1) {
                    Log.d("SCROLL", "END");
                    scrollCategory.setImageResource(R.drawable.refresh);
                }
            }
        });

        ViewGroup.LayoutParams layoutParamList = listCategory.getLayoutParams();
        layoutParamList.height = getCategoriesListHeight();
        listCategory.setLayoutParams(layoutParamList);



        return view;
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
        //TODO ----------------------
        try {
           allItemCategory = ApplicationPropertiesLoader.getLoader(getActivity()).getAllCategories().size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (allItemCategory <= visibleCategoriesCount) {
            listHeight = allItemCategory * categoryItemHeight;
            scrollCategory.setVisibility(View.GONE);
        } else {
            listHeight = visibleCategoriesCount * categoryItemHeight;
            scrollCategory.setVisibility(View.VISIBLE);
        }
        return (int) listHeight;
    }

    //click on category and open panel accessories
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
    public void panelAccessoryHide(Boolean aBoolean) {
        if (!aBoolean) {
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


    @OnClick(R.id.scroll_category)
    public void scrollCategory() {
        if (scroll) {
            int itemHeight = listCategory.getChildAt(0).getHeight();
            int countCategory = categoryWrappers.size() - visibleCategoriesCount;
            listCategory.smoothScrollBy(itemHeight, itemHeight * countCategory);
            scroll = false;
        } else {
            int itemHeight = listCategory.getChildAt(0).getHeight();
            int countCategory = categoryWrappers.size() - visibleCategoriesCount;
            listCategory.smoothScrollBy(itemHeight, -itemHeight * countCategory);
            scroll = true;
        }
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
                if (v.getId() == R.id.button_next) {
                    background.setImageResource(R.drawable.welldone);
                    contentGame.setVisibility(View.INVISIBLE);
                    if (additionalPanel.getTag().equals("open")) {
                        contentGirl.setTranslationX(0);
                        additionalPanel.setVisibility(View.INVISIBLE);
                    }

                    View v1 = contentGirl.getRootView();
                    v1.setDrawingCacheEnabled(true);
                    bitmap = v1.getDrawingCache();

                    background.setImageResource(R.drawable.game);
                    contentGame.setVisibility(View.VISIBLE);
                    if (additionalPanel.getTag().equals("open")) {
                        contentGirl.setTranslationX(-additionalPanel.getWidth());
                        additionalPanel.setVisibility(View.VISIBLE);
                    }

                }
                break;
            case MotionEvent.ACTION_UP: // release
                v.setAlpha(1);
                switch (v.getId()) {
                    case R.id.button_back:
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
                        MainPlayer.getInstance(getActivity()).pause();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("well_done", bitmap);
                        FragmentWellDone fragmentWellDone = new FragmentWellDone();
                        fragmentWellDone.setArguments(bundle);
                        BusProvider.getInstanceMain().post(fragmentWellDone);
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainPlayer.getInstance(getActivity()).resume();
    }

    @Subscribe
    public void subscribeSomeStuff(Boolean object) {
        if (object)
            MainPlayer.getInstance(getActivity()).pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        MainPlayer.getInstance(getActivity()).pause();
    }

    private List<CategoryWrapper> getCategoryWrappers() {
        List<CategoryWrapper> categoryWrappers = new ArrayList<>();

        List<Category> categories = null;
        try {
            categories = ApplicationPropertiesLoader.getLoader(getActivity()).getAllCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Category category : categories) {
            if (category.getCategoryTitle().equals("dress"))
                categoryWrappers.add(new CategoryWrapper(category, getActivity(), true, contentGirl));
            else
                categoryWrappers.add(new CategoryWrapper(category, getActivity(), contentGirl));
        }
        return categoryWrappers;
    }
}