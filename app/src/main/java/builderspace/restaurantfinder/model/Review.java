package builderspace.restaurantfinder.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {


    @SerializedName("author_name")
    @Expose
    private String authorName;


    @SerializedName("author_url")
    @Expose
    private String authorURL;

    @SerializedName("relative_time_description")
    @Expose
    private String relativeTimeDescription;

    @SerializedName("text")
    @Expose
    private String reviewText;

    @SerializedName("rating")
    @Expose
    private Float rating;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("profile_photo_url")
    @Expose
    private String profilePhotoURL;

    @SerializedName("time")
    @Expose
    private Long time;


    public Review() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorURL() {
        return authorURL;
    }

    public void setAuthorURL(String authorURL) {
        this.authorURL = authorURL;
    }

    public String getRelativeTimeDescription() {
        return relativeTimeDescription;
    }

    public void setRelativeTimeDescription(String relativeTimeDescription) {
        this.relativeTimeDescription = relativeTimeDescription;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public void setProfilePhotoURL(String profilePhotoURL) {
        this.profilePhotoURL = profilePhotoURL;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
