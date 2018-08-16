package mastersunny.unitedclub.models;

import java.io.Serializable;

public class RoomImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "RoomImageDTO{" +
                "imageUrl='" + imageUrl + '\'' +
                '}';
    }
}