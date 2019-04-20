package zfani.assaf.guroshots.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoData {

    @SerializedName("items")
    public List<Photo> photoList;

    @SerializedName("success")
    public boolean success;
}
