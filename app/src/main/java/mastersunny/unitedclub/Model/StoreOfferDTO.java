package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 1/25/2018.
 */

public class StoreOfferDTO {
    @SerializedName("offer_id")
    private int offerId;
    @SerializedName("store_dto")
    private StoreDTO storeDTO;
    @SerializedName("offer_category")
    private OfferCategory offerCategory;
    @SerializedName("offer")
    private String offer;
    @SerializedName("end_date")
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

    public OfferCategory getOfferCategory() {
        return offerCategory;
    }

    public void setOfferCategory(OfferCategory offerCategory) {
        this.offerCategory = offerCategory;
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
                ", offerCategory=" + offerCategory +
                ", offer='" + offer + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
