package builderspace.restaurantfinder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to store photo details.
 */

public class Photo {

    @SerializedName("height")
    @Expose
    private Integer height;

    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions = new ArrayList<>();

    @SerializedName("width")
    @Expose
    private Integer width;

    @SerializedName("photo_reference")
    @Expose
    private String photoReference;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
