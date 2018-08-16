package mastersunny.unitedclub.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RoomDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private double latitude;

    private double longitude;

    private String address;

    private String details;

    private int noOfAccomodation;

    private boolean femaleFriendly;

    private boolean tvAvailable;

    private boolean acAvailable;

    private boolean wifiAvailable;

    private boolean lunchAvailable;

    private double lunchCost;

    private boolean transportAvailable;

    private double transportCost;

    private double roomCost;

    private boolean disabled;

    private UserDTO user;

    private String startDate;

    private String endDate;

    private List<RoomImageDTO> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getNoOfAccomodation() {
        return noOfAccomodation;
    }

    public void setNoOfAccomodation(int noOfAccomodation) {
        this.noOfAccomodation = noOfAccomodation;
    }

    public boolean isFemaleFriendly() {
        return femaleFriendly;
    }

    public void setFemaleFriendly(boolean femaleFriendly) {
        this.femaleFriendly = femaleFriendly;
    }

    public boolean isTvAvailable() {
        return tvAvailable;
    }

    public void setTvAvailable(boolean tvAvailable) {
        this.tvAvailable = tvAvailable;
    }

    public boolean isAcAvailable() {
        return acAvailable;
    }

    public void setAcAvailable(boolean acAvailable) {
        this.acAvailable = acAvailable;
    }

    public boolean isWifiAvailable() {
        return wifiAvailable;
    }

    public void setWifiAvailable(boolean wifiAvailable) {
        this.wifiAvailable = wifiAvailable;
    }

    public boolean isLunchAvailable() {
        return lunchAvailable;
    }

    public void setLunchAvailable(boolean lunchAvailable) {
        this.lunchAvailable = lunchAvailable;
    }

    public double getLunchCost() {
        return lunchCost;
    }

    public void setLunchCost(double lunchCost) {
        this.lunchCost = lunchCost;
    }

    public boolean isTransportAvailable() {
        return transportAvailable;
    }

    public void setTransportAvailable(boolean transportAvailable) {
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<RoomImageDTO> getImages() {
        return images;
    }

    public void setImages(List<RoomImageDTO> images) {
        this.images = images;
    }
}
