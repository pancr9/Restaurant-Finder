package builderspace.restaurantfinder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to store opening_hours.
 */

public class OpenHours {

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;

    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekDayText = new ArrayList<>();

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekDayText() {
        return weekDayText;
    }

    public void setWeekDayText(List<Object> weekDayText) {
        this.weekDayText = weekDayText;
    }
}
