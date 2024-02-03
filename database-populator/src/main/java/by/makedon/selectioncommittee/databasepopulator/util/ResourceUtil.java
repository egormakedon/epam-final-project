package by.makedon.selectioncommittee.databasepopulator.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Yahor Makedon
 */
@Slf4j
public final class ResourceUtil {
    public static final String DATABASE_PROPERTIES_FILE_NAME = "database";
    public static final String DATABASE_URL_KEY = "database.url";
    public static final String DATABASE_USER_KEY = "database.user";
    public static final String DATABASE_PASSWORD_KEY = "database.password";

    private static final Map<String, String> propertyMap = new HashMap<>();

    static {
        loadDatabaseProperties();
        log.info("Database properties are loaded successfully");
    }

    @SneakyThrows
    private static void loadDatabaseProperties() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTIES_FILE_NAME);
        propertyMap.put(DATABASE_URL_KEY, resourceBundle.getString(DATABASE_URL_KEY));
        propertyMap.put(DATABASE_USER_KEY, resourceBundle.getString(DATABASE_USER_KEY));
        propertyMap.put(DATABASE_PASSWORD_KEY, resourceBundle.getString(DATABASE_PASSWORD_KEY));
    }

    public String getPropertyByKey(String key) {
        return propertyMap.getOrDefault(key, "");
    }
}
