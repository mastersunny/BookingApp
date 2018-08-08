package mastersunny.unitedclub.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExamDTO implements Serializable {

    private Long id;

    private boolean expired;

    private String examDate;

    private String name;

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

    @Override
    public String toString() {
        return "ExamDTO{" +
                "id=" + id +
                ", expired=" + expired +
                ", examDate='" + examDate + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
