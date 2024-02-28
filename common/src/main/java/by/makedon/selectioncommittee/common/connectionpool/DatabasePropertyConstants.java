package by.makedon.selectioncommittee.common.connectionpool;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DatabasePropertyConstants {
    public static final String DATABASE_RESOURCE_NAME = "database";
    public static final String URL_PROPERTY_KEY = "database.url";
    public static final String USER_PROPERTY_KEY = "database.user";
    public static final String PASSWORD_PROPERTY_KEY = "database.password";
    public static final String POOL_SIZE_PROPERTY_KEY = "database.poolSize";
    public static final String CHARACTER_ENCODING_PROPERTY_KEY = "database.characterEncoding";
    public static final String USE_UNICODE_PROPERTY_KEY = "database.useUnicode";
    public static final String LOGIN_TIMEOUT = "database.loginTimeout";
}
