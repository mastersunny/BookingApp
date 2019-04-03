package mastersunny.rooms.listeners;

import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.RoomDTO;

public interface RoomSearchListener {

    void onRecentSearch(RoomDTO roomDTO);

    void onPlaceSearch(PlaceDTO placeDTO);
}
