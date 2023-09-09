package core.utilities.exceptions;

import java.text.MessageFormat;

public class PropertyException extends RuntimeException {

    public PropertyException(final String message) {
        super(message);
    }

    public PropertyException(String format, Object arg) {
        super(MessageFormat.format(format, arg));
    }
}
