package enums;

public enum URL {
    LIFETIME_MEMBERSHIP("/lifetime-membership-club/", "Страница курса LIFETIME MEMBERSHIP CLUB"),
    LOGIN("/angularjs-protractor/banking/#/login", "Страница Way2Automation Banking App"),
    ADD_CUSTOMER("/angularjs-protractor/banking/#/manager/addCust", "Страница Add customer"),
    AUTHORIZATION("/angularjs-protractor/registeration/#/login", "Страница авторизации"),
    SQL_EX("https://www.sql-ex.ru/", "Страница 'Практическое владение языком SQL'"),
    DROPPABLE("/way2auto_jquery/droppable.php", "Страница Droppable"),
    FRAMES_AND_WINDOWS("/way2auto_jquery/frames-and-windows.php", "Страница Frames And Windows"),
    ALERT("/way2auto_jquery/alert.php", "Страница Alert");

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
