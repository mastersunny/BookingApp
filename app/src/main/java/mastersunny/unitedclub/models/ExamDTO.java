package mastersunny.unitedclub.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExamDTO implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("examDate")
    private String examDate;

    @SerializedName("name")
    private String name;


    public void setId(Long id) {
        this.id = id;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getExamDate() {
        return examDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ExamDTO{" +
                "id=" + id +
                ", examDate='" + examDate + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
