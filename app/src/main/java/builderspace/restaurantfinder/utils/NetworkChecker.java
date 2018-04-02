package builderspace.restaurantfinder.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {

    private static NetworkChecker networkChecker = null;

    private NetworkChecker() {

    }

    public static NetworkChecker getInstance() {
        if (networkChecker == null) {
            networkChecker = new NetworkChecker();
        }
        return networkChecker;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
