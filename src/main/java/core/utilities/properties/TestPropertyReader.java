package core.utilities.properties;

import core.utilities.exceptions.PropertyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class TestPropertyReader {

    private static final String TEST_PROPERTIES_PATH = "/src/test/resources/properties/";
    private ResourceBundle propertyFile;

    public TestPropertyReader(PropertyType propertyType) {
        this.propertyFile = getPropertyFile(propertyType);
    }

    private static String getPropertiesDirectory() {
        String propertiesPath = System.getProperty("user.dir") + TEST_PROPERTIES_PATH;
        if ((new File(propertiesPath)).isDirectory()) {
            return propertiesPath;
        } else {
            throw new PropertyException("Cannot find properties directory: {}", propertiesPath);
        }
    }

    private ResourceBundle getPropertyFile(PropertyType propertyType) {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getPropertiesDirectory() + propertyType.getFileName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            this.propertyFile = new PropertyResourceBundle(stream);
        } catch (IOException e) {
            throw new PropertyException("Cannot find properties file {}!", propertyType.getFileName());
        }
        return propertyFile;
    }

    public String getProperty(Property property) {
        return this.propertyFile.getString(property.getKey());
    }
}
