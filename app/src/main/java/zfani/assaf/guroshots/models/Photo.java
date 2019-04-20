package zfani.assaf.guroshots.models;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    public String photoId;

    @SerializedName("width")
    public int width;

    @SerializedName("height")
    public int height;

    @SerializedName("likes")
    public int likes;

    @SerializedName("votes")
    public int votes;

    @SerializedName("views")
    public int views;
}
