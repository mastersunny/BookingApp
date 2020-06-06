package mastersunny.rooms.listeners;

public interface LoginListener {

    void initLogin();

    void insertPhoneNumber();

    void verifyPhoneNumber(String phoneNumber);

    void customerLogin(String phoneNumber);

    void loginSuccess();

    void loginCanceled();

    void signUp();

}
