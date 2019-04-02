package mastersunny.rooms.models;

public enum ItemType {
    ITEM_TYPE_PLACE(0),
    ITEM_TYPE_ROOM(1),
    ITEM_TYPE_LOCATION(2);

    private final int value;

    ItemType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
