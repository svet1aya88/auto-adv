package core.utilities;

import core.utilities.exceptions.PropertyException;

import java.io.File;

public class TestPropertyReader {

    private static final String TEST_PROPERTIES_PATH = "/src/test/resources/properties/";

    public static String getPropertiesDirectory() {
        String propertiesPath = System.getProperty("user.dir") + TEST_PROPERTIES_PATH;
        if ((new File(propertiesPath)).isDirectory()) {
            return propertiesPath;
        } else {
            throw new PropertyException("Cannot find properties directory: {}", propertiesPath);
        }
    }
}
