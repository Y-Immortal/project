package edu.wschina.a66.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.wschina.a66.Bean.Image;
import edu.wschina.a66.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<Image> imageList;

    public ImageAdapter(List<Image> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {

        Image image = imageList.get(position);
        holder.nameText.setText(image.getName());
        holder.jiageText.setText(image.getJiage());
        holder.juliText.setText(image.getJuli());
        holder.reviewsText.setText(image.getReviews());
        holder.imgView.setImageResource(image.getImg());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView nameText,juliText,jiageText,reviewsText;
        ImageView imgView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.item_name);
            juliText = itemView.findViewById(R.id.item_juli);
            jiageText = itemView.findViewById(R.id.item_jiage);
            reviewsText = itemView.findViewById(R.id.item_reviews);
            imgView = itemView.findViewById(R.id.item_image);
        }
    }
}
