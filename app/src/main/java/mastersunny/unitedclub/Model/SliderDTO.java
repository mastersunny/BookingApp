package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 2/7/2018.
 */

public class SliderDTO {

    @SerializedName("image")
    private String imageUrl;
    @SerializedName("store_id")
    private int storeId;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "SliderDTO{" +
                "imageUrl='" + imageUrl + '\'' +
                ", storeId=" + storeId +
                '}';
    }
}
