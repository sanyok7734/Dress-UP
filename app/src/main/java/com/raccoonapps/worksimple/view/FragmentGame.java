package com.raccoonapps.worksimple.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.adapters.AdapterAccessory;
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
    private RecyclerView.LayoutManager layoutAccessory;
    private ArrayList<Category> itemCategories = new ArrayList<>();

    private Category category;

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


        layoutAccessory = new LinearLayoutManager(getActivity());
        additionalPanel.setLayoutManager(layoutAccessory);

        adapterCategory = new AdapterCategory(itemCategories);
        mLayoutManager = new LinearLayoutManager(getActivity());
        listCategory.setLayoutManager(mLayoutManager);
        listCategory.setAdapter(adapterCategory);
        listCategory.getItemAnimator().setChangeDuration(0);

        return view;
    }

    //click on category and open panel accessories
    @Subscribe
    public void panelAccessoryShow(Category category) {
        this.category = category;
        additionalPanel.setAdapter(new AdapterAccessory(category.getAccessories()));

        ObjectAnimator animXPanel = ObjectAnimator.ofFloat(additionalPanel, "x", contentGirl.getWidth() - additionalPanel.getWidth());
        ObjectAnimator animXScreen = ObjectAnimator.ofFloat(contentGirl, "x", -additionalPanel.getWidth());
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(700);
        animSet.playTogether(animXPanel, animXScreen);
        animSet.start();
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
        }
    }

    // pressing on accessory for him placement
    @Subscribe
    public void accessoryCodrinate(Integer position) {
        ArrayList<Accessory> accessories = category.getAccessories();
        int tag = accessories.get(position).getImage();
        // получаем картинку в Bitmap и передаем координаты для корденирования элемента
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(accessories.get(position).getImage());
        double X = accessories.get(position).getCoordinates().getX();
        double Y = accessories.get(position).getCoordinates().getY();
        category.setCoordinateImage(tag, drawable, X, Y);

        Log.d("OVERFLOV", "добавить");
    }

    private ArrayList<Category> getItemCategories() {
        ArrayList<Category> itemCategories = new ArrayList<>();
        itemCategories.add(new Category(R.drawable.hair_ic, getActivity(), contentGirl, AccessoryManager.getInstance().getAccessory("Hair")));
        itemCategories.add(new Category(R.drawable.hat_ic, getActivity(),  contentGirl, AccessoryManager.getInstance().getAccessory("Hat")));
        itemCategories.add(new Category(R.drawable.bag_ic, getActivity(), contentGirl, AccessoryManager.getInstance().getAccessory("Bag")));
        itemCategories.add(new Category(R.drawable.dress_ic, getActivity(), true, contentGirl, AccessoryManager.getInstance().getAccessory("Dress")));
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

    @Override
    public void onDestroy() {
        super.onPause();
        bus.unregister(this);
    }
}
