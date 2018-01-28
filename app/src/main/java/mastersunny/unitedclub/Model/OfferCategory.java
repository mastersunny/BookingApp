package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 1/25/2018.
 */

public class OfferCategory {
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("total_offer")
    private int totalOffer;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalOffer() {
        return totalOffer;
    }

    public void setTotalOffer(int totalOffer) {
        this.totalOffer = totalOffer;
    }

    @Override
    public String toString() {
        return "OfferCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", totalOffer=" + totalOffer +
                '}';
    }
}
