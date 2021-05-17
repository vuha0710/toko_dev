package io.konekto.backoffice.domain.constant;

/**
 * Application constants.
 */
public final class Constant {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    private Constant() {
    }
}
