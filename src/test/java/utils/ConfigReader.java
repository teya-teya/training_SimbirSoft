package utils;

import io.qameta.allure.Step;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки config.properties");
        }
    }

    @Step("Получить свойство ключу {key}")
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
