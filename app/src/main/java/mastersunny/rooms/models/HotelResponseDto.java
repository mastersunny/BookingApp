package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HotelResponseDto implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("details")
    private String details;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private Object email;

    @SerializedName("contact")
    private String contact;

    @SerializedName("phone")
    private String phone;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("quality")
    private String quality;

    @SerializedName("is_verified")
    private Integer isVerified;

    @SerializedName("distance")
    private double distance;

    @SerializedName("rooms")
    private List<RoomDTO> roomDTOS;

    @SerializedName("images")
    private List<RoomImageDTO> images;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<RoomDTO> getRoomDTOS() {
        return roomDTOS;
    }

    public void setRoomDTOS(List<RoomDTO> roomDTOS) {
        this.roomDTOS = roomDTOS;
    }

    public List<RoomImageDTO> getImages() {
        return images;
    }

    public void setImages(List<RoomImageDTO> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "HotelResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", address='" + address + '\'' +
                ", email=" + email +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", quality='" + quality + '\'' +
                ", isVerified=" + isVerified +
                ", distance=" + distance +
                ", roomDTOS=" + roomDTOS +
                ", images=" + images +
                '}';
    }
}

