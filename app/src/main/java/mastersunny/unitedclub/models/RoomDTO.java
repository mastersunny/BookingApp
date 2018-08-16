package mastersunny.unitedclub.models;

import java.io.Serializable;
import java.util.List;

public class RoomDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private double latitude;

    private double longitude;

    private String address;

    private String details;

    private int noOfAccomodation;

    private boolean femaleFriendly;

    private boolean isTvAvailable;

    private boolean isAcAvailable;

    private boolean isWifiAvailable;

    private boolean isLunchAvailable;

    private double lunchCost;

    private boolean isTransportAvailable;

    private double transportCost;

    private double roomCost;

    private UserDTO user;

    private String startDate;

    private String endDate;

    private boolean disabled;

    private List<RoomImageDTO> images;

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
        return isTvAvailable;
    }

    public void setTvAvailable(boolean tvAvailable) {
        isTvAvailable = tvAvailable;
    }

    public boolean isAcAvailable() {
        return isAcAvailable;
    }

    public void setAcAvailable(boolean acAvailable) {
        isAcAvailable = acAvailable;
    }

    public boolean isWifiAvailable() {
        return isWifiAvailable;
    }

    public void setWifiAvailable(boolean wifiAvailable) {
        isWifiAvailable = wifiAvailable;
    }

    public boolean isLunchAvailable() {
        return isLunchAvailable;
    }

    public void setLunchAvailable(boolean lunchAvailable) {
        isLunchAvailable = lunchAvailable;
    }

    public double getLunchCost() {
        return lunchCost;
    }

    public void setLunchCost(double lunchCost) {
        this.lunchCost = lunchCost;
    }

    public boolean isTransportAvailable() {
        return isTransportAvailable;
    }

    public void setTransportAvailable(boolean transportAvailable) {
        isTransportAvailable = transportAvailable;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<RoomImageDTO> getImages() {
        return images;
    }

    public void setImages(List<RoomImageDTO> images) {
        this.images = images;
    }
}
