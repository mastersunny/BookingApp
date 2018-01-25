package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 1/25/2018.
 */

public class StoreDTO {
    @SerializedName("store_id")
    private int storeId;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("banner_img")
    private String bannerImg;

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
                '}';
    }
}
