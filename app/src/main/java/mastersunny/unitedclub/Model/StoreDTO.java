package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 1/25/2018.
 */

public class StoreDTO implements Serializable {
    @SerializedName("store_id")
    private int storeId;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("store_image_url")
    private String imageUrl;
    @SerializedName("store_offer_count")
    private int totalOffer;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalOffer() {
        return totalOffer;
    }

    public void setTotalOffer(int totalOffer) {
        this.totalOffer = totalOffer;
    }

    @Override
    public String toString() {
        return "StoreDTO{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", totalOffer=" + totalOffer +
                '}';
    }
}
