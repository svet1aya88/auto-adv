package core.utilities.exceptions;

import java.text.MessageFormat;

public class TestException extends RuntimeException {

    public TestException(String format, Object arg) {
        super(MessageFormat.format(format, arg));
    }

    public TestException(String format, Object... arguments) {
        super(MessageFormat.format(format, arguments));
    }
}
