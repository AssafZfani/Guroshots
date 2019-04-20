package zfani.assaf.guroshots.viewmodels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zfani.assaf.guroshots.Constants;
import zfani.assaf.guroshots.models.Photo;
import zfani.assaf.guroshots.models.PhotoData;
import zfani.assaf.guroshots.retrofit.APIClient;
import zfani.assaf.guroshots.retrofit.APIInterface;

public class PhotoViewModel extends ViewModel {

    private MutableLiveData<List<Photo>> photoList;
    private int page = 0, itemsPerPage, currentItemsPerPage;

    public LiveData<List<Photo>> getPhotoList() {
        if(photoList == null){
            photoList = new MutableLiveData<>();
            loadPhotos();
        }
        return photoList;
    }

    private void loadPhotos() {
        APIClient.getClient().create(APIInterface.class).getPhotos(Constants.MEMBER_ID, page, 50, true, true).enqueue(new Callback<PhotoData>() {
            @Override
            public void onResponse(@NonNull Call<PhotoData> call, @NonNull Response<PhotoData> response) {
                PhotoData photoData = response.body();
                if (photoData == null || !photoData.success) {
                    return;
                }
                itemsPerPage = photoData.photoList.size();
                if (itemsPerPage == 0) {
                    currentItemsPerPage = 0;
                }
                currentItemsPerPage += itemsPerPage;
                if (page == 0) {
                    photoList.postValue(photoData.photoList);
                } else {
                    List<Photo> currentPhotoList = photoList.getValue();
                    if (currentPhotoList != null) {
                        currentPhotoList.addAll(photoData.photoList);
                        photoList.setValue(currentPhotoList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoData> call, @NonNull Throwable t) {

            }
        });
    }

    public void moveToNextPage() {
        this.page++;
        loadPhotos();
    }

    public int getCurrentItemsPerPage() {
        return currentItemsPerPage;
    }
}
