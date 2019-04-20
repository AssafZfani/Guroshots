package zfani.assaf.guroshots.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import zfani.assaf.guroshots.R;
import zfani.assaf.guroshots.viewmodels.DetailsViewModel;

public class PhotoDetailsFragment extends Fragment {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivToolbarImage)
    ImageView ivToolbarImage;
    @BindView(R.id.ivLike)
    ImageView ivLike;
    private DetailsViewModel detailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);
        ivBack.setOnClickListener(v -> requireActivity().onBackPressed());
        detailsViewModel = ViewModelProviders.of(requireActivity()).get(DetailsViewModel.class);
        loadPhotoDetails();
        return view;
    }

    private void loadPhotoDetails() {
        detailsViewModel.getSelectedPhoto().observe(this, photo -> {
            Picasso.get().load(getString(R.string.big_image_url, photo.width, photo.height, photo.photoId)).into(ivToolbarImage);
            ivLike.setOnClickListener(v -> {
                photo.likes++;
                Toast.makeText(requireContext(), getString(R.string.successful_vote), Toast.LENGTH_SHORT).show();
            });
        });
    }
}
