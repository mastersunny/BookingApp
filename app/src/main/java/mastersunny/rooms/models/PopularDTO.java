package mastersunny.rooms.models;

/**
 * Created by ASUS on 1/21/2018.
 */

public class PopularDTO {
    private String companyName;
    private int totalOffer;
    private String imgUrl;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getTotalOffer() {
        return totalOffer;
    }

    public void setTotalOffer(int totalOffer) {
        this.totalOffer = totalOffer;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "PopularDTO{" +
                "companyName='" + companyName + '\'' +
                ", totalOffer=" + totalOffer +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
