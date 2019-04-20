package zfani.assaf.guroshots.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import zfani.assaf.guroshots.R;
import zfani.assaf.guroshots.interfaces.OnPhotoSelectedListener;
import zfani.assaf.guroshots.models.Photo;

public class PhotoAdapter extends ListAdapter<Photo, PhotoAdapter.PhotoViewHolder> {

    private final OnPhotoSelectedListener onPhotoSelectedListener;

    public PhotoAdapter(OnPhotoSelectedListener onPhotoSelectedListener) {
        super(new DiffUtil.ItemCallback<Photo>() {
            @Override
            public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
                return oldItem.photoId.equalsIgnoreCase(newItem.photoId);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
                return oldItem.likes == newItem.likes && oldItem.votes == newItem.votes && oldItem.views == newItem.views;
            }
        });
        this.onPhotoSelectedListener = onPhotoSelectedListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(View.inflate(parent.getContext(), R.layout.row_photo, null));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = getItem(position);
        Context context = holder.itemView.getContext();
        Picasso.get().load(context.getString(R.string.small_image_url, photo.photoId)).into(holder.ivImage);
        holder.tvLikes.setText(context.getString(R.string.likes, photo.likes));
        holder.tvVotes.setText(context.getString(R.string.votes, photo.votes));
        holder.tvViews.setText(context.getString(R.string.views, photo.views));
        holder.itemView.setOnClickListener(v -> {
            if (onPhotoSelectedListener != null) {
                onPhotoSelectedListener.onPhotoSelected(photo);
            }
        });
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvLikes)
        TextView tvLikes;
        @BindView(R.id.tvVotes)
        TextView tvVotes;
        @BindView(R.id.tvViews)
        TextView tvViews;
        @BindView(R.id.tvLiked)
        TextView tvLiked;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
