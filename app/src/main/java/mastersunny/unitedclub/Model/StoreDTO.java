package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 1/25/2018.
 */

public class StoreDTO implements Serializable {
    @SerializedName("id")
    private int storeId;
    @SerializedName("name")
    private String storeName;
    @SerializedName("bannerImage")
    private String bannerImg;
    private int totalOffer;

    public int getTotalOffer() {
        return totalOffer;
    }

    public void setTotalOffer(int totalOffer) {
        this.totalOffer = totalOffer;
    }

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

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    @Override
    public String toString() {
        return "StoreDTO{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", bannerImg='" + bannerImg + '\'' +
                ", totalOffer=" + totalOffer +
                '}';
    }
}
