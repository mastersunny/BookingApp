package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ApiResponse implements Serializable {

    @SerializedName("message")
    String message;

    @SerializedName("divisions")
    List<DivisionResponseDto> divisions;

    @SerializedName("districts")
    List<DistrictResponseDto> districts;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DivisionResponseDto> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionResponseDto> divisions) {
        this.divisions = divisions;
    }

    public List<DistrictResponseDto> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictResponseDto> districts) {
        this.districts = districts;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", divisions=" + divisions +
                ", districts=" + districts +
                '}';
    }
}
