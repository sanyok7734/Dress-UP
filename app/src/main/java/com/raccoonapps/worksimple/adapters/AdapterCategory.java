package com.raccoonapps.worksimple.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.components.Category;
import com.raccoonapps.worksimple.view.FragmentGame;

import java.util.ArrayList;
import java.util.Collections;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {

    private ArrayList<Category> categories;

    public AdapterCategory(ArrayList<Category> categories) {
        Collections.sort(categories);
        this.categories = categories;
    }

    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterCategory.ViewHolder holder, final int position) {
        final Category category = categories.get(position);
        holder.iconCategory.setImageDrawable(category.getDrawableCategory());
        holder.iconButton.setImageDrawable(category.getDrawableButton());

        holder.iconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.isCheck()) {
                    category.setDrawableButton(R.drawable.btn_right);
                    category.setCheck(false);
                    notifyItemChanged(position);
                    FragmentGame.bus.post(false);
                } else {
                    for (int i = 0; i < categories.size(); i++) {
                        categories.get(i).setDrawableButton(R.drawable.btn_right);
                        categories.get(i).setCheck(false);
                        notifyItemChanged(i);
                    }
                    category.setDrawableButton(R.drawable.btn_right_p);
                    category.setCheck(true);
                    notifyItemChanged(position);
                    FragmentGame.bus.post(category);
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
