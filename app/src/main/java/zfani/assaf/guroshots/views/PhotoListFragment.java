package zfani.assaf.guroshots.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import zfani.assaf.guroshots.R;
import zfani.assaf.guroshots.adapters.PhotoAdapter;
import zfani.assaf.guroshots.viewmodels.DetailsViewModel;
import zfani.assaf.guroshots.viewmodels.PhotoViewModel;

public class PhotoListFragment extends Fragment {

    @BindView(R.id.skvProgress)
    SpinKitView skvProgress;
    @BindView(R.id.rvPhotos)
    RecyclerView rvPhotos;
    private PhotoViewModel photoViewModel;
    private PhotoAdapter photoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        ButterKnife.bind(this, view);
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        initRecyclerView();
        observeData();
        return view;
    }

    private void initRecyclerView() {
        rvPhotos.setAdapter(photoAdapter = new PhotoAdapter(photo -> {
            ViewModelProviders.of(requireActivity()).get(DetailsViewModel.class).setSelectedPhoto(photo);
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.flContainer, new PhotoDetailsFragment()).addToBackStack(PhotoDetailsFragment.class.getName()).commit();
        }));
        rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) Objects.requireNonNull(rvPhotos.getLayoutManager())).findLastVisibleItemPosition();
                if (lastVisibleItem == photoViewModel.getCurrentItemsPerPage() - 1) {
                    skvProgress.setVisibility(View.VISIBLE);
                    photoViewModel.moveToNextPage();
                }
            }
        });
    }

    private void observeData() {
        photoViewModel.getPhotoList().observe(this, photoList -> {
            skvProgress.setVisibility(View.GONE);
            photoAdapter.submitList(photoList);
        });
    }
}
