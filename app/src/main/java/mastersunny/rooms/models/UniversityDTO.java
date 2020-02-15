package mastersunny.rooms.models;

import java.io.Serializable;

public class UniversityDTO implements Serializable {

    private Long id;

    private String name;

    private String imageUrl;

    private DivisionResponseDto place;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DivisionResponseDto getPlace() {
        return place;
    }

    public void setPlace(DivisionResponseDto place) {
        this.place = place;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "UniversityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", place=" + place +
                '}';
    }
}
