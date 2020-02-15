package mastersunny.rooms.listeners;

import mastersunny.rooms.models.DistrictResponseDto;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.RoomDTO;

public interface RoomSearchListener {

    void onRecentSearch(RoomDTO roomDTO);

    void onPlaceSearch(DivisionResponseDto placeDTO);

    void onLocalitySearch(DistrictResponseDto localityDTO);
}
