package mastersunny.rooms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;

    @SerializedName("image_url")
    private String imageUrl;

    public RoomImageDTO() {
    }

    public RoomImageDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "RoomImageDTO{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}