package core.utilities.controls;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class JavaScriptActions {

    public static SelenideElement clickWithJS(SelenideElement element) {
        executeJavaScript("arguments[0].click();", element);
        return element;
    }

    public static SelenideElement scrollIntoView(SelenideElement element) {
        executeJavaScript("arguments[0].scrollIntoView();", element);
        return element;
    }
}
