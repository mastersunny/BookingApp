package mastersunny.rooms.listeners;

import mastersunny.rooms.models.CustomerResponseDto;

public interface LoginListener {

    void initLogin();

    void insertPhoneNumber();

    void verifyPhoneNumber(String phoneNumber);

    void customerLogin(String phoneNumber);

    void signUp(String phoneNumber);

    void customerRegister(CustomerResponseDto customerResponseDto);

    void loginCanceled();



}
