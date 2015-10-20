package com.raccoonapps.worksimple.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.Squeezing;
import com.raccoonapps.worksimple.view.FragmentGame;

import java.util.ArrayList;
import java.util.List;

public class AdapterAccessory extends RecyclerView.Adapter<AdapterAccessory.ViewHolder> {

    private List<Accessory> accessories = new ArrayList<>();
    Context context;

    public AdapterAccessory(List<Accessory> accessories, Context context) {
        this.accessories = accessories;
        this.context = context;
    }

    @Override
    public AdapterAccessory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accessory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterAccessory.ViewHolder holder, final int position) {
        final Accessory accessory = accessories.get(position);
        holder.iconAccessory.setImageResource(accessory.getImage());

        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(accessory.getImage());
        ViewGroup.LayoutParams params = holder.iconAccessory.getLayoutParams();
        params.height = (int) (Squeezing.occupy * drawable.getIntrinsicHeight()) / 100;
        params.width = (int) (Squeezing.occupy * drawable.getIntrinsicWidth()) / 100;
        holder.iconAccessory.setLayoutParams(params);

        holder.iconAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentGame.bus.post(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accessories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconAccessory;

        public ViewHolder(View itemView) {
            super(itemView);
            iconAccessory = (ImageView) itemView.findViewById(R.id.item_accessory);
        }

    }
}
