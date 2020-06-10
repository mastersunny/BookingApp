package mastersunny.rooms.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CustomerRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_no")
    private String mobileNo;

    @SerializedName("address")
    private String address;

    @SerializedName("age")
    private int age;

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("nid")
    private String nid;

    @SerializedName("gender")
    private String gender;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("image")
    private String image="";

    public CustomerRequestDto() {
    }

    public CustomerRequestDto(String name, String email, String mobileNo, String address, int age, String city, String country, String nid, String gender, String dateOfBirth, String image) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.address = address;
        this.age = age;
        this.city = city;
        this.country = country;
        this.nid = nid;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CustomerRequestDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", nid='" + nid + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
