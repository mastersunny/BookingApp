package mastersunny.rooms.listeners;

public interface LoginListener {

    void initLogin();

    void insertPhoneNumber();

    void verifyPhoneNumber(String phoneNumber);

    void loginSuccess();

    void loginCanceled();

    void signUp();

}
