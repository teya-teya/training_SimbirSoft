package enums;

public enum URL {
    LIFETIME_MEMBERSHIP("/lifetime-membership-club/", "Страница курса LIFETIME MEMBERSHIP CLUB"),
    LOGIN("/angularjs-protractor/banking/#/login", "Страница Way2Automation Banking App"),
    ADD_CUSTOMER("/angularjs-protractor/banking/#/manager/addCust", "Страница Add customer"),
    AUTHORIZATION("/angularjs-protractor/registeration/#/login", "Страница авторизации");

    private final String url;
    private final String description;

    URL(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
