package utils;

import net.datafaker.Faker;

import java.util.Locale;

public class RandomUtils {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String fullName() {
        return faker.name().fullName();
    }

    public static String username() { return  faker.name().username().toLowerCase();}

    public static String password() {
        return faker.internet().password(12, 18, true, true, true);
    }

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String postCode() { return faker.address().zipCode(); }

    public static int getRandomNum(int min, int max) { return faker.random().nextInt(min, max); }
}