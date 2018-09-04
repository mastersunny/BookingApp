package mastersunny.unitedclub.models;

import java.io.Serializable;
import java.time.LocalDate;


public class RoomBookingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private RoomBookingIdDTO pk;

    private String startDate;

    private String endDate;

    private double roomCost;

    private String currentStatus;

    private int noOfAccommodation;

    private String createdAt;

    public RoomBookingIdDTO getPk() {
        return pk;
    }

    public void setPk(RoomBookingIdDTO pk) {
        this.pk = pk;
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

    public double getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(double roomCost) {
        this.roomCost = roomCost;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getNoOfAccommodation() {
        return noOfAccommodation;
    }

    public void setNoOfAccommodation(int noOfAccommodation) {
        this.noOfAccommodation = noOfAccommodation;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RoomBookingDTO{" +
                "pk=" + pk +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", roomCost=" + roomCost +
                ", currentStatus='" + currentStatus + '\'' +
                ", noOfAccommodation=" + noOfAccommodation +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
