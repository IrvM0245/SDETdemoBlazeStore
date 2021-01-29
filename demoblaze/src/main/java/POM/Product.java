package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Product {
    public WebDriver driver;

    @FindBy(xpath = "//div//a[@class='btn btn-success btn-lg']")
    WebElement buttonAdd;

    @FindBy(xpath = "//div[@id='navbarExample']//ul//li//a")
    WebElement home;

    @FindBy(xpath = "//a[@id='cartur']")
    WebElement carLink;

    @FindBy(xpath = "//div[@id='more-information']//strong")
    WebElement productDescription;

    public Product(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getButtonAdd() {
        return buttonAdd;
    }

    public WebElement getCarLink() {
        return carLink;
    }

    public WebElement getProductDescription() {
        return productDescription;
    }

    public WebElement getHome() {
        return home;
    }
}
