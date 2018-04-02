package builderspace.restaurantfinder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Model class to store result into restaurant.
 */

public class Restaurant {

    @SerializedName("place_id")
    @Expose
    private String resID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("id")
    @Expose
    private String iconID;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("opening_hours")
    @Expose
    private OpenHours openHours;

    @SerializedName("photos")
    @Expose
    private List<Photo> photos = new ArrayList<>();

    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("reference")
    @Expose
    private String reference;

    @SerializedName("scope")
    @Expose
    private String scope;

    @SerializedName("types")
    @Expose
    private List<String> types = new ArrayList<>();

    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    @SerializedName("price_level")
    @Expose
    private Integer priceLevel;

    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;

    public Restaurant(String resID, String name, String icon, String iconID, Geometry geometry, OpenHours openHours, List<Photo> photos, Double rating, String reference, String scope, List<String> types, String vicinity, Integer priceLevel, String formattedAddress) {
        this.resID = resID;
        this.name = name;
        this.icon = icon;
        this.iconID = iconID;
        this.geometry = geometry;
        this.openHours = openHours;
        this.photos = photos;
        this.rating = rating;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.vicinity = vicinity;
        this.priceLevel = priceLevel;
        this.formattedAddress = formattedAddress;
    }

    public Restaurant() {

    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconID() {
        return iconID;
    }

    public void setIconID(String iconID) {
        this.iconID = iconID;
    }

    public builderspace.restaurantfinder.model.Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(builderspace.restaurantfinder.model.Geometry geometry) {
        this.geometry = geometry;
    }

    public OpenHours getOpenHours() {
        return openHours;
    }

    public void setOpenHours(OpenHours openHours) {
        this.openHours = openHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }
}
