package enums;

public enum URL {
    LIFETIME_MEMBERSHIP("/lifetime-membership-club/", "Страница курса LIFETIME MEMBERSHIP CLUB");

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
