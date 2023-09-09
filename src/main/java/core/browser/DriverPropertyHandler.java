package core.browser;

import core.utilities.exceptions.PropertyException;

public class DriverPropertyHandler {

    private static final String DRIVERS_PATH = "/src/main/resources/drivers/";

    public static String getChromeDriverPath() {
        return getDriversPath(DriverSingleton.DriverType.CHROME);
    }

    public static String getFirefoxDriverPath() {
        return getDriversPath(DriverSingleton.DriverType.FIREFOX);
    }

    private static String getDriversPath(DriverSingleton.DriverType driverType) {
        String driverFileName;
        switch (driverType) {
            case FIREFOX:
                driverFileName = "geckodriver.exe";
                break;
            case CHROME:
                driverFileName = "chromedriver.exe";
                break;
            default:
                throw new PropertyException("{} browser type is not defined in code!");
        }
        return System.getProperty("user.dir") + DRIVERS_PATH + driverFileName;
    }
}
