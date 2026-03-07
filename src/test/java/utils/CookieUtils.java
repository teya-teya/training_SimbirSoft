package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Set;

public class CookieUtils {

    private static final String COOKIE_FILE = "cookies.data";

    public static void saveCookies(WebDriver driver) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COOKIE_FILE))) {
            Set<Cookie> cookies = driver.manage().getCookies();
            oos.writeObject(cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCookies(WebDriver driver) {
        File file = new File(COOKIE_FILE);
        if (!file.exists()) {
            System.out.println("Cookie file not found — авторизуемся заново");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
            if (cookies == null || cookies.isEmpty()) return;

            driver.manage().deleteAllCookies();

            for (Cookie cookie : cookies) {
                try {
                    driver.manage().addCookie(cookie);
                } catch (Exception e) {
                    System.out.println("Не удалось добавить куку " + cookie.getName() + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при загрузке cookies: " + e.getMessage());
        }
    }

    public static boolean cookieFileExists() {
        return new File(COOKIE_FILE).exists();
    }
}