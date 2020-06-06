package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomBookingRequestDto implements Serializable {

    @SerializedName("room_id")
    private Long roomId;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("guest_no")
    private int guestNo;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getGuestNo() {
        return guestNo;
    }

    public void setGuestNo(int guestNo) {
        this.guestNo = guestNo;
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

    @Override
    public String toString() {
        return "RoomBookingRequestDto{" +
                "roomId=" + roomId +
                ", userId=" + userId +
                ", guestNo=" + guestNo +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
