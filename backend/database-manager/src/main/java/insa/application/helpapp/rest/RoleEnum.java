package insa.application.helpapp.rest;

public enum RoleEnum {
    USER(1),
    VOLUNTEER(2),
    ADMIN(3);

    private final int value;

    // Constructor
    RoleEnum(int value) {
        this.value = value;
    }

    // Getter
    public int getValue() {
        return value;
    }
}
