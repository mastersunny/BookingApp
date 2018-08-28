package mastersunny.unitedclub.models;

import java.io.Serializable;
import java.time.LocalDate;


public class RoomBookingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private RoomBookingIdDTO pk;
	
	private LocalDate startDate;

	private LocalDate endDate;

	private double roomCost;

	private String currentStatus;
	
	private int noOfAccommodation;

	private String createdAt;

	private String createdBy;

	private String updatedAt;

	private String updatedBy;

	public RoomBookingIdDTO getPk() {
		return pk;
	}

	public void setPk(RoomBookingIdDTO pk) {
		this.pk = pk;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
