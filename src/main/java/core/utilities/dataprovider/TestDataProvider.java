package core.utilities.dataprovider;

import core.utilities.exceptions.PropertyException;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import core.utilities.random.Randomizer;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TestDataProvider {

    private static final TestPropertyReader PROPERTY_READER = new TestPropertyReader(PropertyType.LOGIN);
    private static final String USERNAME = PROPERTY_READER.getProperty(LoginProperty.USER_DEFAULT_LOGIN);
    private static final String PASSWORD = PROPERTY_READER.getProperty(LoginProperty.USER_DEFAULT_PASSWORD);
    private static final String INVALID_USERNAME = "invalid username";
    private static final String INVALID_PASSWORD = "invalid password";
    private static final String EMPTY_VALUE = " ";
    private static final int FILTER_NAME_MIN_LENGTH = 3;
    private static final int FILTER_NAME_MAX_LENGTH = 128;

    public static Object[][] getData(DataType dataType) {
        switch (dataType) {
            case LOGIN_CREDS -> {
                return new Object[][]{
                        {EMPTY_VALUE, EMPTY_VALUE},
                        {EMPTY_VALUE, PASSWORD},
                        {USERNAME, EMPTY_VALUE},
                        {INVALID_USERNAME, PASSWORD},
                        {USERNAME, INVALID_PASSWORD},
                        {INVALID_USERNAME, INVALID_PASSWORD}};
            }
            case FILTER_NAME -> {
                return new Object[][]{
                {Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MIN_LENGTH)},
                {Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH)},
                {Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MAX_LENGTH, FILTER_NAME_MAX_LENGTH)},
                {Randomizer.generateStringWithSpecialSymbols(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH)}};
            }
            default -> throw new PropertyException("Test data type {} is not defined!", dataType);
        }
    }

    public static Stream<Arguments> getDataJunit(DataType dataType) {
        switch (dataType) {
            case LOGIN_CREDS -> {
                return Stream.of(
                        Arguments.of(EMPTY_VALUE, EMPTY_VALUE),
                        Arguments.of(EMPTY_VALUE, PASSWORD),
                        Arguments.of(USERNAME, EMPTY_VALUE),
                        Arguments.of(INVALID_USERNAME, PASSWORD),
                        Arguments.of(USERNAME, INVALID_PASSWORD),
                        Arguments.of(INVALID_USERNAME, INVALID_PASSWORD)
                );
            }
            case FILTER_NAME -> {
                return Stream.of(
                        Arguments.of(Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MIN_LENGTH)),
                        Arguments.of(Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH)),
                        Arguments.of(Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MAX_LENGTH, FILTER_NAME_MAX_LENGTH)),
                        Arguments.of(Randomizer.generateStringWithSpecialSymbols(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH))
                );
            }
            default -> throw new PropertyException("Test data type {} is not defined!", dataType);
        }
    }
}
