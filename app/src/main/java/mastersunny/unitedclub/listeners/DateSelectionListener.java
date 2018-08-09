package mastersunny.unitedclub.listeners;

public interface DateSelectionListener {

    void startDate(String date);

    void endDate(String date);

    void totalRoom(int roomCount);

    void totalGuest(int guestCount);
}
