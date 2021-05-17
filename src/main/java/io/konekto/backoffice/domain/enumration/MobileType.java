package io.konekto.backoffice.domain.enumration;

import io.konekto.backoffice.exception.BadRequestException;

public enum MobileType {
    IOS("ios"),
    ANDROID("android");

    private String value;

    private MobileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MobileType fromValue(String type) {
        for (MobileType st : MobileType.values()) {
            if (st.getValue().equalsIgnoreCase(type.trim())) {
                return st;
            }
        }
        throw new BadRequestException("Type not support");
    }
}
