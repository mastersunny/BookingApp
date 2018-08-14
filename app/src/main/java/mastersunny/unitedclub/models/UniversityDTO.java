package mastersunny.unitedclub.models;

import java.io.Serializable;

public class UniversityDTO implements Serializable {

    private Long id;

    private String name;

    private PlaceDTO place;

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

    public PlaceDTO getPlace() {
        return place;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "UniversityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place=" + place +
                '}';
    }
}