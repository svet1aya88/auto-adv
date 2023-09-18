package core.browser;

public class DriverPropertyHandler {

    private static final String DRIVERS_PATH = "/src/main/resources/drivers/";

    public static String getChromeDriverPath() {
        return getDriverPath(DriverSingleton.DriverType.CHROME);
    }

    public static String getFirefoxDriverPath() {
        return getDriverPath(DriverSingleton.DriverType.FIREFOX);
    }

    private static String getDriverPath(DriverSingleton.DriverType driverType) {
        String driverFileName = switch (driverType) {
            case FIREFOX -> "geckodriver.exe";
            case CHROME -> "chromedriver.exe";
        };
        return System.getProperty("user.dir") + DRIVERS_PATH + driverFileName;
    }
}
