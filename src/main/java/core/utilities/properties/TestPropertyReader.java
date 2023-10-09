package core.utilities.properties;

import core.utilities.exceptions.PropertyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class TestPropertyReader {

    private static final String TEST_PROPERTIES_PATH = "/src/test/resources/properties/";
    private ResourceBundle propertyFile;

    public TestPropertyReader(PropertyType propertyType) {
        this.propertyFile = getPropertyFile(propertyType);
    }

    private static String getPropertiesDirectory(String stage) {
        String propertiesPath = System.getProperty("user.dir") + TEST_PROPERTIES_PATH + stage + "/";
        if ((new File(propertiesPath)).isDirectory()) {
            return propertiesPath;
        } else {
            throw new PropertyException("Cannot find properties directory: {}", propertiesPath);
        }
    }

    private ResourceBundle getPropertyFile(PropertyType propertyType) {
        String stageProperty = System.getProperty("stage");
        if (Objects.isNull(stageProperty)) {
            stageProperty = PropertyStage.QA.getName();
        }
        FileInputStream stream;
        try {
            stream = new FileInputStream(getPropertiesDirectory(stageProperty) + propertyType.getFileName());
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
