package zfani.assaf.guroshots.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import zfani.assaf.guroshots.models.Photo;

public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<Photo> selectedPhoto;

    public DetailsViewModel() {
        this.selectedPhoto = new MutableLiveData<>();
    }

    public MutableLiveData<Photo> getSelectedPhoto() {
        return selectedPhoto;
    }

    public void setSelectedPhoto(Photo selectedPhoto) {
        this.selectedPhoto.setValue(selectedPhoto);
    }
}
