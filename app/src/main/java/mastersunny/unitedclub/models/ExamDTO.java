package mastersunny.unitedclub.models;

import java.io.Serializable;

public class ExamDTO implements Serializable {

    private Long id;

    private boolean expired;

    private String examDate;

    private String name;

    private PlaceDTO place;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
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
        return "ExamDTO{" +
                "id=" + id +
                ", expired=" + expired +
                ", examDate='" + examDate + '\'' +
                ", name='" + name + '\'' +
                ", place=" + place +
                '}';
    }
}
