package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 1/25/2018.
 */

public class StoreOfferDTO implements Serializable {
    @SerializedName("offer_id")
    private int offerId;
    @SerializedName("store")
    private StoreDTO storeDTO;
    @SerializedName("category")
    private CategoryDTO categoryDTO;
    @SerializedName("offer")
    private String offer;
    @SerializedName("endDate")
    private String endDate;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public StoreDTO getStoreDTO() {
        return storeDTO;
    }

    public void setStoreDTO(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "StoreOfferDTO{" +
                "offerId=" + offerId +
                ", storeDTO=" + storeDTO +
                ", categoryDTO=" + categoryDTO +
                ", offer='" + offer + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
