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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COOKIE_FILE))) {
            driver.manage().deleteAllCookies();
            Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        } catch (Exception e) {
            System.out.println("Cookie file not found — авторизуемся заново");
        }
    }

    public static boolean cookieFileExists() {
        return new File(COOKIE_FILE).exists();
    }
}