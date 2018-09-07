package mastersunny.unitedclub.models;

import java.io.Serializable;

public class ExamDTO implements Serializable {

    private Long id;

    private UniversityDTO university;

    private String unitName;

    private String examDate;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return "ExamDTO{" +
                "id=" + id +
                ", university=" + university +
                ", unitName='" + unitName + '\'' +
                ", examDate='" + examDate + '\'' +
                '}';
    }
}
