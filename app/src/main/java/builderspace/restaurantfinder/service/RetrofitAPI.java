package builderspace.restaurantfinder.service;

import builderspace.restaurantfinder.model.Result;
import builderspace.restaurantfinder.model.ResultDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface to include calls.
 */
public interface RetrofitAPI {

    @GET("textsearch/json")
    Call<Result> getResultFromTextSearch(@Query("query") String query,
                                         @Query("type") String type,
                                         @Query("key") String key,
                                         @Query("radius") Integer radius,
                                         @Query("maxprice") Integer maxPrice,
                                         @Query("opennow") Boolean openNow);

    @GET("details/json")
    Call<ResultDetails> getRestaurantById(@Query("placeid") String placeId,
                                          @Query("key") String key);

}
