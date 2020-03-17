package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

public class BannerResponseDto {

    @SerializedName("image_url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BannerResponseDto{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
