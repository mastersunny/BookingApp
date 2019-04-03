package mastersunny.rooms.models;

import java.io.Serializable;

public class PlaceDTO implements Serializable{

    private Long id;

    private String name;

    private String bnName;

    private String imageUrl;

    private double latitude;

    private double longitude;

    private int itemType = 0;

    public PlaceDTO() {
    }

    public PlaceDTO(String name, String bnName, String imageUrl) {
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

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bnName='" + bnName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}

