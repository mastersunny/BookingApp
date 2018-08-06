package mastersunny.unitedclub.utils;

public enum SearchType {
    TYPE_NEARBY(7), TYPE_PLACE(3);

    private final int type;

    SearchType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return type;
    }
}
