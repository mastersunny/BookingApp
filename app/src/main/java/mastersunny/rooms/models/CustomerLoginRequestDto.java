package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerLoginRequestDto implements Serializable {

    @SerializedName("mobile_no")
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
