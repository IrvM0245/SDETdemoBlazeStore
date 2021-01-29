package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Home {
    public WebDriver driver;

    @FindBy(xpath = "//div[@id='tbodyid']")
    WebElement productsTable;

    @FindBy(xpath = "//a[@id='nava']")
    WebElement pageTitle;

    public Home(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getPageTitle() {
        return pageTitle;
    }

    public WebElement getProductsTable() {
        return productsTable;
    }
}
