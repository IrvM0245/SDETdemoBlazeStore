package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Car {
    WebDriver driver;

    @FindBy(xpath = "//tbody[@id='tbodyid']//tr")
    List<WebElement> productsAdded;

    @FindBy(xpath = "//button[@class='btn btn-success']")
    WebElement placeOrderButton;

    @FindBy(xpath = "//div[@class='col-lg-8']//h2")
    WebElement productsLabel;

    @FindBy(xpath = "//input[@id='name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@id='country']")
    WebElement inputCountry;
    @FindBy(xpath = "//input[@id='city']")
    WebElement inputCity;
    @FindBy(xpath = "//input[@id='card']")
    WebElement inputCreditCard;
    @FindBy(xpath = "//input[@id='month']")
    WebElement inputMonth;
    @FindBy(xpath = "//input[@id='year']")
    WebElement inputYear;
    @FindBy(xpath = "//div[@id='orderModal']//button[@Class='btn btn-primary']")
    WebElement buttonPurchase;
    @FindBy(xpath = "//div[@Class='sweet-alert  showSweetAlert visible']//h2")
    WebElement purchaseAlert;
    @FindBy(xpath = "//div[@Class='sweet-alert  showSweetAlert visible']//p")
    WebElement purchaseData;
    @FindBy(xpath = "//div[@Class='sweet-alert  showSweetAlert visible']//button[@class='confirm btn btn-lg btn-primary']")
    WebElement closePurchase;


    public Car(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public List<WebElement> getProductsAdded() {
        return productsAdded;
    }

    public WebElement getPlaceOrderButton() {
        return placeOrderButton;
    }

    public WebElement getProductsLabel() {
        return productsLabel;
    }

    public WebElement getInputName() {
        return inputName;
    }

    public WebElement getInputCountry() {
        return inputCountry;
    }

    public WebElement getInputCity() {
        return inputCity;
    }

    public WebElement getInputCreditCard() {
        return inputCreditCard;
    }

    public WebElement getInputMonth() {
        return inputMonth;
    }

    public WebElement getInputYear() {
        return inputYear;
    }

    public WebElement getButtonPurchase() {
        return buttonPurchase;
    }

    public WebElement getPurchaseAlert() {
        return purchaseAlert;
    }

    public WebElement getPurchaseData() {
        return purchaseData;
    }

    public WebElement getClosePurchase() {
        return closePurchase;
    }
}
