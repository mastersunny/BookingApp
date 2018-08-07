package mastersunny.unitedclub.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("enabled")
    private boolean enabled;

    @SerializedName("profileImage")
    private String profileImage;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("createdBy")
    private Long createdBy;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("updatedBy")
    private Long updatedBy;

    @SerializedName("deleted")
    private boolean deleted;

    @SerializedName("defaultAgent")
    private boolean defaultAgent;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDefaultAgent() {
        return defaultAgent;
    }

    public void setDefaultAgent(boolean defaultAgent) {
        this.defaultAgent = defaultAgent;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", enabled=" + enabled +
                ", profileImage='" + profileImage + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy=" + createdBy +
                ", updatedAt='" + updatedAt + '\'' +
                ", updatedBy=" + updatedBy +
                ", deleted=" + deleted +
                ", defaultAgent=" + defaultAgent +
                '}';
    }
}
