package mastersunny.unitedclub.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ASUS on 2/19/2018.
 */

public class ResponseModel implements Serializable {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("access_token")
    private String accessToken;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AccessModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
