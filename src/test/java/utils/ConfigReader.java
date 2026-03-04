package utils;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                log.info("✅ Файл config.properties успешно загружен");
            } else {
                log.error("❌ Файл config.properties не найден");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки config.properties", e);
        }
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null) {
            log.info("Используется системное свойство для '{}'", key);
            return systemValue;
        }

        String envKey = key.toUpperCase().replace(".", "_");
        String envValue = System.getenv(envKey);
        if (envValue != null) {
            log.info("Используется переменная окружения '{}' для '{}'", envKey, key);
            return envValue;
        }

        String fileValue = properties.getProperty(key);
        if (fileValue != null) {
            log.info("Используется значение из config.properties для '{}'", key);
            return fileValue;
        }

        log.warn("Ключ '{}' не найден в конфигурации", key);
        return null;
    }
}