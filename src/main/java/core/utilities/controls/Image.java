package core.utilities.controls;

import lombok.Getter;
import org.openqa.selenium.WebElement;

public class Image extends CustomControl {

    public Image(WebElement element, String name) {
        super(element, name);
    }
}
