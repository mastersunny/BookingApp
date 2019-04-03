package mastersunny.rooms.models;

public enum ItemType {
    ITEM_TYPE_LOCATION(0),
    ITEM_TYPE_RECENT_HEADER(1),
    ITEM_TYPE_RECENT_SEARCH(2),
    ITEM_TYPE_ALL_CITY_HEADER(3),
    ITEM_TYPE_ALL_CITY(4);

    private final int value;

    ItemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
