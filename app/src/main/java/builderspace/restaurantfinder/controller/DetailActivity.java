package builderspace.restaurantfinder.controller;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import builderspace.restaurantfinder.R;
import builderspace.restaurantfinder.model.Photo;
import builderspace.restaurantfinder.model.RestaurantDetail;
import builderspace.restaurantfinder.model.ResultDetails;
import builderspace.restaurantfinder.service.RetrofitAPI;
import builderspace.restaurantfinder.service.ServiceCreator;
import builderspace.restaurantfinder.utils.NetworkChecker;
import builderspace.restaurantfinder.view.ViewPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class DetailActivity extends AppCompatActivity {

    private String resId;
    private RetrofitAPI retrofitAPI;
    private ProgressDialog progressDialog;
    private IntentFilter connectivityIntentFilter;
    private List<String> photoLinks = new ArrayList<>();

    /* Declare Views */
    private TextView tvTitle, tvSubTitle;
    private RelativeLayout relContact, relWebsite;

    private RestaurantDetail detail = null;
    /**
     * Receiver to check information of Network Changes
     */

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkChecker.getInstance().isNetworkAvailable(context)) {
                Snackbar.make(findViewById(R.id.scrolling), getString(R.string.no_active_connection), Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                if (resId != null) getRestaurantById(resId);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        connectivityIntentFilter = new IntentFilter(CONNECTIVITY_ACTION);

        resId = getIntent().getStringExtra(getString(R.string.intent_key_id_tag));
        tvTitle = findViewById(R.id.tvTitle);
        tvSubTitle = findViewById(R.id.tvSubtitle);
        relContact = findViewById(R.id.relContact);
        relWebsite = findViewById(R.id.relWebsite);


        retrofitAPI = ServiceCreator.createService(RetrofitAPI.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        if (resId != null) getRestaurantById(resId);
        /*
         * Open URL Intent
         */
        relWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detail.getWebsite() != null) {
                    String uri = detail.getWebsite().trim();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(uri));
                    startActivity(i);
                }
            }
        });
        /*
         * Open call dialer Intent
         */
        relContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detail.getFormattedPhone() != null) {
                    String uri = "tel:" + detail.getFormattedPhone().trim();
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse(uri));
                    startActivity(i);
                }
            }
        });

        /*
         * Handle swipe and refresh
         */
        ((SwipeRefreshLayout) findViewById(R.id.swipeRefresh)).setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (resId != null) {
                    progressDialog.show();
                    getRestaurantById(resId);
                }
            }
        });

    }

    /**
     * Call to get Restaurant details via retrofit
     */
    private void getRestaurantById(String placeId) {

        Call<ResultDetails> detailsCall = retrofitAPI.getRestaurantById(placeId, getResources().getString(R.string.API_KEY_2));

        detailsCall.enqueue(new Callback<ResultDetails>() {

            /**
             *
             * Check for response and fill in the views with the information
             */
            @Override
            public void onResponse(@NonNull Call<ResultDetails> call, @NonNull Response<ResultDetails> response) {
                if (response.isSuccessful()) {

                    detail = response.body().getRestaurantDetail();
                    if (detail != null) {

                        //Set views.
                        photoLinks = new ArrayList<>();

                        tvTitle.setText(detail.getName());

                        if (detail.getFormattedAddress() != null)
                            tvSubTitle.setText(detail.getFormattedAddress());
                        else tvSubTitle.setVisibility(View.GONE);

                        if (detail.getFormattedPhone() != null)
                            ((TextView) findViewById(R.id.tvPhone)).setText(detail.getFormattedPhone());
                        else relContact.setVisibility(View.GONE);

                        if (detail.getPriceLevel() != null)
                            ((RatingBar) findViewById(R.id.rating_price)).setRating(detail.getPriceLevel());

                        if (detail.getRating() != null)
                            ((RatingBar) findViewById(R.id.rating_review)).setRating(detail.getRating());

                        if (detail.getWebsite() == null)
                            relWebsite.setVisibility(View.GONE);

                        if (detail.getPhotos() != null) {
                            for (Photo p : detail.getPhotos()) {
                                photoLinks.add(p.getPhotoReference());
                            }
                        }

                        //Toast.makeText(DetailActivity.this, "Review size" + detail.getReviews().size(), Toast.LENGTH_SHORT).show();
                    }
                }
                ((SwipeRefreshLayout) findViewById(R.id.swipeRefresh)).setRefreshing(false);
                progressDialog.dismiss();
                setUpPhotos();
            }

            @Override
            public void onFailure(@NonNull Call<ResultDetails> call, @NonNull Throwable t) {
                ((SwipeRefreshLayout) findViewById(R.id.swipeRefresh)).setRefreshing(false);
                progressDialog.dismiss();
            }
        });

    }

    /**
     * Load photos into viewpager and retrofit returns ArrayList of photo references
     */
    private void setUpPhotos() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, photoLinks);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, connectivityIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
