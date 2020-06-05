package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 2/19/2018.
 */

public class RestModel implements Serializable {

    @SerializedName("meta_data")
    private MetaData metaData;
    @SerializedName("data")
    private CustomerResponseDto userDTO;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public CustomerResponseDto getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(CustomerResponseDto userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public String toString() {
        return "RestModel{" +
                "metaData=" + metaData +
                ", userDTO=" + userDTO +
                '}';
    }
}
