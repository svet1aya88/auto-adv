package business.pages.filters;

import business.pages.launches.AddEditFilterPopupBlock;
import business.pages.launches.LaunchesPage;
import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.Link;
import core.utilities.controls.TextInput;
import core.utilities.exceptions.TestException;
import core.utilities.waits.Waiter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class FiltersPage {

    @FindBy(css = "input[class*='inputSearch__input']")
    private WebElement searchInput;

    @FindBy(css = "[class*='filterPageToolbar'] button")
    private WebElement addFilterBtn;

    @FindBy(css = "[class*='filterName'] a")
    private List<WebElement> filterNameLinks;

    @FindBy(css = "[class*='filterName__pencil-icon']")
    private WebElement pencilBtn;

    public FiltersPage() {
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public List<String> getAllFilterNamesFromTable() {
        Waiter.waitForListOfElements(filterNameLinks, "Filter name links");
        return filterNameLinks.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public FiltersPage fillSearchInput(String name) {
        new TextInput(searchInput, "Search input").fillText(name);
        return this;
    }

    public LaunchesPage clickAddFilterButton() {
        new Button(addFilterBtn, "Add filter button").click();
        return new LaunchesPage();
    }

    public AddEditFilterPopupBlock clickFilterEditButton(String filterName) {
        new Link(getFilterNameLink(filterName), "Filter link").hover();
        new Button(pencilBtn, "Edit button").click();
        return new AddEditFilterPopupBlock();
    }

    private WebElement getFilterNameLink(String name) {
        Waiter.waitForListOfElements(filterNameLinks, "Filter name links");
        return filterNameLinks.stream()
                .filter(link -> link.getText().equalsIgnoreCase(name))
                .findFirst().orElseThrow(() -> new TestException("Filter link with name '{}' isn't found!", name));
    }
}
