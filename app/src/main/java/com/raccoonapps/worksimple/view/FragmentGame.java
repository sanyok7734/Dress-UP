package com.raccoonapps.worksimple.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.adapters.AdapterCategory;
import com.raccoonapps.worksimple.components.Category;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.AccessoryManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentGame extends Fragment {

    private AdapterCategory adapterCategory;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Category> itemCategories = new ArrayList<>();

    public static Bus bus = new Bus();

    private boolean scroll = true;

    @Bind(R.id.list_category)
    public RecyclerView listCategory;
    @Bind(R.id.additional_panel)
    public RecyclerView additionalPanel;
    @Bind(R.id.content_girl)
    public FrameLayout contentGirl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_play_game, container, false);
        ButterKnife.bind(this, view);
        bus.register(this);
        itemCategories = getItemCategories();

        adapterCategory = new AdapterCategory(itemCategories, getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        listCategory.setLayoutManager(mLayoutManager);
        listCategory.setAdapter(adapterCategory);
        listCategory.getItemAnimator().setSupportsChangeAnimations(false);

        return view;
    }

    @Subscribe
    public void panelAccessoryShow(ArrayList<Accessory> accessories) {
        if (additionalPanel.getVisibility() == View.INVISIBLE) {
            TranslateAnimation anim = new TranslateAnimation(0, -additionalPanel.getWidth(), 0, 0);
            anim.setDuration(700);
            TranslateAnimation anim2 = new TranslateAnimation(0, -additionalPanel.getWidth(), 0, 0);
            anim2.setDuration(700);
            anim.setAnimationListener(new AnimationListener(true));
            additionalPanel.startAnimation(anim);
            contentGirl.startAnimation(anim2);
        }

        
    }

    @Subscribe
    public void panelAccessoryHide(Boolean aBoolean) {
        if (!aBoolean) {
            TranslateAnimation anim = new TranslateAnimation(0, additionalPanel.getWidth(), 0, 0);
            anim.setDuration(700);
            TranslateAnimation anim2 = new TranslateAnimation(0, additionalPanel.getWidth(), 0, 0);
            anim2.setDuration(700);
            anim.setAnimationListener(new AnimationListener(false));
            additionalPanel.startAnimation(anim);
            contentGirl.startAnimation(anim2);
        }
    }

    private ArrayList<Category> getItemCategories() {
        ArrayList<Category> itemCategories = new ArrayList<>();
        itemCategories.add(new Category(R.drawable.hair_ic, getActivity(), contentGirl, AccessoryManager.getInstance().getAccessory("Hair")));
        itemCategories.add(new Category(R.drawable.hat_ic, getActivity(), contentGirl, null));
        itemCategories.add(new Category(R.drawable.bag_ic, getActivity(), contentGirl, null));
        itemCategories.add(new Category(R.drawable.dress_ic, getActivity(), contentGirl, null));
        itemCategories.add(new Category(R.drawable.necklace_ic, getActivity(), contentGirl, null));
        itemCategories.add(new Category(R.drawable.earrings_ic, getActivity(), contentGirl, null));
        itemCategories.add(new Category(R.drawable.shoes_ic, getActivity(), contentGirl, null));
        return itemCategories;
    }

    @OnClick(R.id.scroll_category)
    public void scrollCategory() {
        if (scroll) {
            int itemHeight = listCategory.getChildAt(0).getHeight();
            int countCategory = itemCategories.size() - 7;
            listCategory.smoothScrollBy(itemHeight, itemHeight * countCategory);
            scroll = false;
        } else {
            int itemHeight = listCategory.getChildAt(0).getHeight();
            int countCategory = itemCategories.size() - 7;
            listCategory.smoothScrollBy(itemHeight, -itemHeight * countCategory);
            scroll = true;
        }


    }


    private class AnimationListener implements Animation.AnimationListener {

        private boolean isCheck;

        public AnimationListener(boolean isCheck) {
            this.isCheck = isCheck;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isCheck) {
                additionalPanel.clearAnimation();
                contentGirl.clearAnimation();
                additionalPanel.setTranslationX(-additionalPanel.getWidth());
                contentGirl.setTranslationX(-additionalPanel.getWidth());
                additionalPanel.setVisibility(View.VISIBLE);
            } else {
                additionalPanel.clearAnimation();
                contentGirl.clearAnimation();
                additionalPanel.setTranslationX(contentGirl.getWidth());
                contentGirl.setTranslationX(0);
                additionalPanel.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
