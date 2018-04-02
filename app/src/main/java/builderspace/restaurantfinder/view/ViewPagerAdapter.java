package builderspace.restaurantfinder.view;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import builderspace.restaurantfinder.R;

/**
 * Adapter for the viewpager used for photos of restaurants.
 * Uses Picasso to load images into the view
 */


public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<String> photoLinks;

    public ViewPagerAdapter(Context mContext, List<String> photoLinks) {
        this.mContext = mContext;
        this.photoLinks = photoLinks;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return photoLinks.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = mLayoutInflater.inflate(R.layout.view_pager_item, container, false);

        ImageView imageView = imageLayout.findViewById(R.id.image_view_pager);
        Picasso.with(mContext)
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=1000&photoreference=" + photoLinks.get(position) + "&key=" + mContext.getResources().getString(R.string.API_KEY_2))
                .into(imageView);

        container.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
