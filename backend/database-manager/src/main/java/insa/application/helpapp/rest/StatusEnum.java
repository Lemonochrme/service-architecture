package insa.application.helpapp.rest;

public enum StatusEnum {
    WAITING(1),
    VALIDATED(2),
    REJECTED(3),
    SELECTED(4),
    FINISHED(5);

    private final int value;

    // Constructor
    StatusEnum(int value) {
        this.value = value;
    }

    // Getter
    public int getValue() {
        return value;
    }
}