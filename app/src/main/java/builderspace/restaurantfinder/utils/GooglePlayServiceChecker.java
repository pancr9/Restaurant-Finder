package builderspace.restaurantfinder.utils;


import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class GooglePlayServiceChecker {
    public boolean checkGooglePlayServices(Context c) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(c);
        return result == ConnectionResult.SUCCESS;

    }
}
