package mastersunny.unitedclub.models;

import java.io.Serializable;

public class ExamDTO implements Serializable {

    private Long id;

    private UniversityDTO university;

    private String name;

    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UniversityDTO getUniversity() {
        return university;
    }

    public void setUniversity(UniversityDTO university) {
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExamDTO{" +
                "id=" + id +
                ", university=" + university +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
