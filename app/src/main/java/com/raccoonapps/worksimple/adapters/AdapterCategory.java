package com.raccoonapps.worksimple.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.components.CategoryWrapper;
import com.raccoonapps.worksimple.view.FragmentGame;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    private List<CategoryWrapper> categories;

    public AdapterCategory(List<CategoryWrapper> categories) {
        this.categories = categories;
    }

    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterCategory.ViewHolder holder, final int position) {
        final CategoryWrapper categoryWrapper = categories.get(position);
        holder.iconCategory.setImageDrawable(categoryWrapper.getDrawableCategory());
        holder.iconButton.setImageDrawable(categoryWrapper.getDrawableButton());

        holder.iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryWrapper.isSelectedCategory()) {
                    categoryWrapper.setDrawableButton(R.drawable.btn_right);
                    categoryWrapper.setSelectedCategory(false);
                    notifyItemChanged(position);
                    FragmentGame.bus.post(false);
                } else {
                    for (int i = 0; i < categories.size(); i++) {
                        categories.get(i).setDrawableButton(R.drawable.btn_right);
                        categories.get(i).setSelectedCategory(false);
                        notifyItemChanged(i);
                    }
                    categoryWrapper.setDrawableButton(R.drawable.btn_right_p);
                    categoryWrapper.setSelectedCategory(true);
                    notifyItemChanged(position);
                    FragmentGame.bus.post(categoryWrapper);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconCategory;
        public  ImageView iconButton;

        public ViewHolder(View itemView) {
            super(itemView);
            iconCategory = (ImageView) itemView.findViewById(R.id.image_category);
            iconButton = (ImageView) itemView.findViewById(R.id.button_category);
            iconButton.setImageResource(R.drawable.btn_right);
        }

    }
}
