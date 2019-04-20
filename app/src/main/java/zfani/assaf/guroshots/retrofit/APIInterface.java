package zfani.assaf.guroshots.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import zfani.assaf.guroshots.models.PhotoData;

public interface APIInterface {

    @Headers({"X-API-VERSION: 20", "X-ENV: ANDROID"})
    @FormUrlEncoded
    @POST("rest_mobile/get_photos_public")
    Call<PhotoData> getPhotos(
            @Field("member_id") String id,
            @Field("start") int start,
            @Field("limit") int limit,
            @Field("get_likes") boolean getLikes,
            @Field("get_votes") boolean getVotes
    );
}
