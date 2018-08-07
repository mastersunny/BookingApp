package mastersunny.unitedclub.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExamDTO implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("examDate")
    private String examDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                ", examDate='" + examDate + '\'' +
                '}';
    }
}
