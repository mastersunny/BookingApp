package mastersunny.rooms.models;


import java.io.Serializable;


public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private String nid;

    private String sscRegNo;

    private String hscRegNo;

    private String profileImage;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String nid, String sscRegNo, String hscRegNo) {
        this.name = name;
        this.email = email;
        this.nid = nid;
        this.sscRegNo = sscRegNo;
        this.hscRegNo = hscRegNo;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getSscRegNo() {
        return sscRegNo;
    }

    public void setSscRegNo(String sscRegNo) {
        this.sscRegNo = sscRegNo;
    }

    public String getHscRegNo() {
        return hscRegNo;
    }

    public void setHscRegNo(String hscRegNo) {
        this.hscRegNo = hscRegNo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", nid='" + nid + '\'' +
                ", sscRegNo='" + sscRegNo + '\'' +
                ", hscRegNo='" + hscRegNo + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
