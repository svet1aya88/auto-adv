package core.utilities.waits;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JavascriptWaits {
    PAGE_LOAD("if(document.readyState!==\"complete\"){return false;}return true"),
    JQUERY_AJAX("if(typeof window.jQuery!==\"undefined\")" +
            "{if(window.jQuery.active){return false;}" +
            "else if(window.jQuery.ajax && window.jQuery.ajax.active){return false;}}return true");
    private final String jsCode;
}
