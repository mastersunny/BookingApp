package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ApiResponse implements Serializable {

    @SerializedName("message")
    String message;

    @SerializedName("divisions")
    List<DivisionResponseDto> divisions;

    @SerializedName("districts")
    List<DistrictResponseDto> districts;

    @SerializedName("banners")
    List<BannerResponseDto> banners;

    @SerializedName("hotels")
    List<HotelResponseDto> hotels;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DivisionResponseDto> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionResponseDto> divisions) {
        this.divisions = divisions;
    }

    public List<DistrictResponseDto> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictResponseDto> districts) {
        this.districts = districts;
    }

    public List<BannerResponseDto> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerResponseDto> banners) {
        this.banners = banners;
    }

    public List<HotelResponseDto> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelResponseDto> hotels) {
        this.hotels = hotels;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", divisions=" + divisions +
                ", districts=" + districts +
                ", banners=" + banners +
                ", hotels=" + hotels +
                '}';
    }
}
