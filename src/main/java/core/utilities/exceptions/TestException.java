package core.utilities.exceptions;

import org.slf4j.helpers.MessageFormatter;

public class TestException extends RuntimeException {

    public TestException(String format, Object arg) {
        super(MessageFormatter.format(format, arg).getMessage());
    }

    public TestException(String format, Object... arguments) {
        super(MessageFormatter.format(format, arguments).getMessage());
    }
}
