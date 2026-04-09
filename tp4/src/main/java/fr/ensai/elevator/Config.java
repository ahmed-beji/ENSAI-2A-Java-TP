package fr.ensai.elevator;

import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Config {

    /**
     * This utility class should not be instantiated
     */
    private Config() {
    }

    private static Map<String, Object> cfg;

    static {
        try (InputStream input = Config.class
                .getClassLoader()
                .getResourceAsStream("application.yml")) {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            cfg = mapper.readValue(input, new TypeReference<Map<String, Object>>() {
            });

        } catch (Exception e) {
            throw new RuntimeException("Cannot load configuration", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Object getValue(String key) {

        Object value = cfg;
        StringBuilder path = new StringBuilder();

        for (String k : key.split("\\.")) {

            if (!(value instanceof Map)) {
                throw new IllegalArgumentException(
                        "Invalid configuration path: '" + path + "'");
            }

            Map<String, Object> map = (Map<String, Object>) value;

            if (!map.containsKey(k)) {
                throw new IllegalArgumentException(
                        "Configuration key not found: '" + key + "'");
            }

            value = map.get(k);

            if (!path.isEmpty()) {
                path.append(".");
            }
            path.append(k);
        }

        return value;
    }

    public static int getInt(String key) {
        return ((Number) getValue(key)).intValue();
    }

    public static double getDouble(String key) {
        return ((Number) getValue(key)).doubleValue();
    }

    public static String get(String key) {
        return getValue(key).toString();
    }
}