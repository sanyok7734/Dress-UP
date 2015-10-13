package com.raccoonapps.worksimple.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.view.FragmentGame;

import java.util.ArrayList;

/**
 * Created by sanyok on 11.10.15.
 */
public class AdapterAccessory extends RecyclerView.Adapter<AdapterAccessory.ViewHolder> {

    private ArrayList<Accessory> accessories = new ArrayList<>();

    public AdapterAccessory(ArrayList<Accessory> accessories) {
        this.accessories = accessories;
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
        holder.iconAccessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentGame.bus.post(position);
                Log.d("SANOOOO", "Click" + position);
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
