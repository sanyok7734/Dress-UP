package com.raccoonapps.worksimple.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.components.CategoryWrapper;
import com.raccoonapps.worksimple.eventbus.BusProvider;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    private List<CategoryWrapper> categories;
    private Context context;

    public AdapterCategory(List<CategoryWrapper> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }


    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterCategory.ViewHolder holder, final int position) {
        final CategoryWrapper categoryWrapper = categories.get(position);
        holder.iconButton.setBackgroundDrawable(categoryWrapper.getDrawableButton());

        holder.iconButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        holder.iconButton.setBackgroundDrawable(categoryWrapper.getDrawableButtonPressed());
                        break;
                    case MotionEvent.ACTION_UP:
                        if (categoryWrapper.isSelectedCategory()) {
                            categoryWrapper.setDrawableButton(categoryWrapper.getDrawableButtonDefault());
                            categoryWrapper.setSelectedCategory(false);
                            notifyItemChanged(position);
                            BusProvider.getInstanceGame().post("Panel");
                        } else {
                            resetCategoryIcon();
                            categoryWrapper.setDrawableButton(categoryWrapper.getDrawableButtonPressed());
                            categoryWrapper.setSelectedCategory(true);
                            notifyItemChanged(position);
                            BusProvider.getInstanceGame().post(categoryWrapper);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (!categoryWrapper.isSelectedCategory()) {
                            holder.iconButton.setBackgroundDrawable(categoryWrapper.getDrawableButtonDefault());
                        }
                        break;
                }
                return true;
            }
        });
    }


    public void resetCategoryIcon() {
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setDrawableButton(categories.get(i).getDrawableButtonDefault());
            categories.get(i).setSelectedCategory(false);
            notifyItemChanged(i);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton iconButton;

        public ViewHolder(View itemView) {
            super(itemView);
            iconButton = (ImageButton) itemView.findViewById(R.id.button_category);
        }
    }
}
