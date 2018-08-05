package mastersunny.unitedclub.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 2/19/2018.
 */

public class RestModel implements Serializable {

    @SerializedName("meta_data")
    private MetaData metaData;
    @SerializedName("data")
    private UserDTO userDTO;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
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
