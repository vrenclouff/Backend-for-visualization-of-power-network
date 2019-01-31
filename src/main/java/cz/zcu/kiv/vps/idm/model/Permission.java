package cz.zcu.kiv.vps.idm.model;

/**
 * Created by Lukas Cerny.
 */
public enum  Permission {
    READ    (0b00000100),
    WRITE   (0b00000010),

    ;
    private final int value;

    Permission(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
