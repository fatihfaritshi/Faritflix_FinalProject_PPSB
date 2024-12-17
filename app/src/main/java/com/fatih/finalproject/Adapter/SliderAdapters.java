package com.fatih.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fatih.finalproject.Domain.SliderItems;
import com.fatih.finalproject.R;

import java.util.List;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {
    private List<SliderItems> SliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapters(List<com.fatih.finalproject.Domain.SliderItems> sliderItems, ViewPager2 viewPager2) {
        SliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapters.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.slide_item_container,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapters.SliderViewHolder holder, int position) {
        holder.setImage(SliderItems.get(position));
        if(position==SliderItems.size()-2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return SliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SliderItems sliderItems){
            RequestOptions requestOptions=new RequestOptions();
            requestOptions=requestOptions.transforms(new CenterCrop(),new RoundedCorners(60));

            Glide.with(context)
                    .load(sliderItems.getImage())
                    .apply(requestOptions)
                    .into(imageView);
        }
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            SliderItems.addAll(SliderItems);
            notifyDataSetChanged();
        }
    };
}
