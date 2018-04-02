package builderspace.restaurantfinder.service;

import android.support.annotation.NonNull;

import builderspace.restaurantfinder.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIService {

    private RetrofitAPI retrofitAPI = ServiceCreator.createService(RetrofitAPI.class);

    private fetchResults mFetchResults;
    private String KEY = "AIzaSyDzkJaXGLibesCe4zA4oV0dUsFcXyMag2w";

    public APIService(fetchResults mFetchResults) {
        this.mFetchResults = mFetchResults;
    }

    public void getPlaceSearch(String query, String type, Integer radius, Integer maxPrice, Boolean openOnly) {

        Call<Result> callTextSearch = retrofitAPI.getResultFromTextSearch(query, type, KEY, radius, maxPrice, openOnly);

        callTextSearch.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {

                if (response.isSuccessful() && response.body() != null) {
                    mFetchResults.parseResults(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {

            }
        });

    }

    public interface fetchResults {
        void parseResults(Result result);
    }

}
