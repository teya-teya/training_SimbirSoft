package enums;

public enum CONTACTS {
    // контакты в хэдере
    TEL_1_1("+919711-111-558", "номер телефона 1"),
    TEL_2_1("+919711-191-558", "номер телефона 2"),
    TEL_3("+1 646-480-0603", "номер телефона 3"),
    SKYPE("seleniumcoaching", "skype"),
    EMAIL("trainer@way2automation.com", "электронная почта"),
    // контакты в футере
    TEL_1_2("+91 97111-11-558", "номер телефона 1"),
    TEL_2_2("+91 97111-91-558", "номер телефона 2"),
    EMAIL_2("seleniumcoaching@gmail.com", "электронная почта");

    private final String value;
    private final String description;

    CONTACTS(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

