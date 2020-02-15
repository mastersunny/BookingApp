package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class DivisionResponseDto implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("bn_name")
    private String bnName;

    @SerializedName("img_url")
    private String imageUrl;

    private double latitude;

    private double longitude;

    private List<DistrictResponseDto> localityDTOS = Collections.EMPTY_LIST;

    public DivisionResponseDto() {
    }

    public DivisionResponseDto(String name, String bnName, String imageUrl) {
        this.name = name;
        this.bnName = bnName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBnName() {
        return bnName;
    }

    public void setBnName(String bnName) {
        this.bnName = bnName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<DistrictResponseDto> getLocalityDTOS() {
        return localityDTOS;
    }

    public void setLocalityDTOS(List<DistrictResponseDto> localityDTOS) {
        this.localityDTOS = localityDTOS;
    }

    @Override
    public String toString() {
        return "DivisionResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bnName='" + bnName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}

