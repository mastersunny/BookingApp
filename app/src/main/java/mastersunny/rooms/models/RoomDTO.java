package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RoomDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;

    @SerializedName("hotel_id")
    private Long hotelId;

    @SerializedName("title")
    private String title;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("description")
    private String description;

//    @SerializedName("")
//    private int noOfAccommodation;

    @SerializedName("female_friendly")
    private int femaleFriendly;

    @SerializedName("is_tv_available")
    private int tvAvailable;

    @SerializedName("is_ac_available")
    private int acAvailable;

    @SerializedName("is_wifi_available")
    private int wifiAvailable;

    @SerializedName("is_lunch_available")
    private int lunchAvailable;

    @SerializedName("lunch_cost")
    private double lunchCost;

    @SerializedName("is_transport_available")
    private int transportAvailable;

    @SerializedName("transport_cost")
    private double transportCost;

    @SerializedName("price")
    private double roomCost;

    @SerializedName("images")
    private List<RoomImageDTO> images;

    public RoomDTO() {
    }

    public RoomDTO(String century_residence_park, double v, double v1, int i) {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFemaleFriendly() {
        return femaleFriendly;
    }

    public void setFemaleFriendly(int femaleFriendly) {
        this.femaleFriendly = femaleFriendly;
    }

    public int getTvAvailable() {
        return tvAvailable;
    }

    public void setTvAvailable(int tvAvailable) {
        this.tvAvailable = tvAvailable;
    }

    public int getAcAvailable() {
        return acAvailable;
    }

    public void setAcAvailable(int acAvailable) {
        this.acAvailable = acAvailable;
    }

    public int getWifiAvailable() {
        return wifiAvailable;
    }

    public void setWifiAvailable(int wifiAvailable) {
        this.wifiAvailable = wifiAvailable;
    }

    public int getLunchAvailable() {
        return lunchAvailable;
    }

    public void setLunchAvailable(int lunchAvailable) {
        this.lunchAvailable = lunchAvailable;
    }

    public double getLunchCost() {
        return lunchCost;
    }

    public void setLunchCost(double lunchCost) {
        this.lunchCost = lunchCost;
    }

    public int getTransportAvailable() {
        return transportAvailable;
    }

    public void setTransportAvailable(int transportAvailable) {
        this.transportAvailable = transportAvailable;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(double roomCost) {
        this.roomCost = roomCost;
    }

    public List<RoomImageDTO> getImages() {
        return images;
    }

    public void setImages(List<RoomImageDTO> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", title='" + title + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", femaleFriendly=" + femaleFriendly +
                ", tvAvailable=" + tvAvailable +
                ", acAvailable=" + acAvailable +
                ", wifiAvailable=" + wifiAvailable +
                ", lunchAvailable=" + lunchAvailable +
                ", lunchCost=" + lunchCost +
                ", transportAvailable=" + transportAvailable +
                ", transportCost=" + transportCost +
                ", roomCost=" + roomCost +
                ", images=" + images +
                '}';
    }
}
