package utils;

import io.qameta.allure.Step;
import net.datafaker.Faker;

import java.util.Locale;

public class RandomUtils {
    private static final Faker faker = new Faker(new Locale("en"));

    @Step("Сгенерировать полное имя")
    public static String fullName() {
        return faker.name().fullName();
    }

    @Step("Сгенерировать username")
    public static String username() { return  (faker.name().firstName() + faker.name().lastName()).toLowerCase();}

    @Step("Сгенерировать пароль")
    public static String password() {
        return faker.internet().password(12, 18, true, true, true);
    }

    @Step("Сгенерировать email")
    public static String email() {
        return faker.internet().emailAddress();
    }

    @Step("Сгенерировать почтовый индекс")
    public static String postCode() { return faker.address().zipCode(); }

    @Step("Сгенерировать случайное число")
    public static int getRandomNum(int min, int max) { return faker.random().nextInt(min, max); }
}