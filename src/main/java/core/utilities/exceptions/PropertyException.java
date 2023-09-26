package core.utilities.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class PropertyException extends RuntimeException {

    public PropertyException(final String message) {
        super(message);
    }

    public PropertyException(String format, Object arg) {
        super(MessageFormatter.format(format, arg).getMessage());
    }
}
