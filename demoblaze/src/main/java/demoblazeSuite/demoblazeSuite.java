package demoblazeSuite;

import POM.Car;
import POM.Home;
import POM.Product;
import Util.ExcelRead;
import Util.model.RegistroModel;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class demoblazeSuite {
    static WebDriver driver;
    ExcelRead manejoArchivo = new ExcelRead();
    List<RegistroModel> listaRegistro;
    WebDriverWait wait;

    @BeforeTest
    private void configuration() throws IOException, InvalidFormatException {
        listaRegistro = manejoArchivo.LecturaArchivo();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        driver.get("https://www.demoblaze.com/");
        Assert.assertEquals(driver.getTitle(),"STORE");
    }

    @Test(priority=0)
    private void homePage(){
        if(wait.until(ExpectedConditions.textToBe(By.xpath("//a[@id='cat']"),"CATEGORIES"))){
            Home home = new Home(driver);
            Assert.assertEquals(home.getPageTitle().getText(), "PRODUCT STORE");
        }
    }
    @Test(priority=1)
    private void addingProducts() {
        selectProducts(3);
    }
    @Test(priority=2)
    private void payingAndCreatingAccount() {
        placingOrderAndCreatingAccount();
    }

    private void selectProducts(Integer n) {
        Home home;
        Product productPage;
        for(int i=0;i<n;i++) {
            if(i>0){
                wait.until(ExpectedConditions.textToBe(By.xpath("//a[@id='cat']"),"CATEGORIES"));
            }
            home = new Home(driver);
            selectedProductDinamic(i+1,home).click();
            if (wait.until(ExpectedConditions.textToBe(By.xpath("//div[@id='more-information']//strong"), "Product description"))) {
                productPage = new Product(driver);
                productPage.getButtonAdd().click();
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
                wait.withTimeout(Duration.ofSeconds(1));
                if(i<n-1) {
                    productPage.getHome().click();
                    wait.withTimeout(Duration.ofSeconds(1));
                }else{
                     productPage.getCarLink().click();
                     if(wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='col-lg-8']//h2"),"Products"))) {
                         Car car = new Car(driver);
                         List<WebElement> elementos = car.getProductsAdded();
                         wait.withTimeout(Duration.ofSeconds(1));
                         Assert.assertEquals(car.getProductsLabel().getText(), "Products");
                         Integer numeroElementos = elementos.size();
                         //Assert.assertEquals( numeroElementos.toString(), n.toString());
                     }else{
                        System.out.println("no aparecio");
                     }
                }
            }
        }
    }

    private void placingOrderAndCreatingAccount() {
        if(wait.until(ExpectedConditions.textToBe(By.xpath("//div[@class='col-lg-8']//h2"),"Products"))){
            Car car = new Car(driver);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='page-wrapper']//button[@class='btn btn-success']")));
            car.getPlaceOrderButton().click();
            int identityNumber = randomGenerateANumber(listaRegistro.size());
            car.getInputName().sendKeys(listaRegistro.get(identityNumber).getName());
            car.getInputCountry().sendKeys(listaRegistro.get(identityNumber).getCountry());
            car.getInputCity().sendKeys(listaRegistro.get(identityNumber).getCity());
            car.getInputCreditCard().sendKeys(listaRegistro.get(identityNumber).getCreditCard());
            car.getInputMonth().sendKeys(listaRegistro.get(identityNumber).getMonth());
            car.getInputYear().sendKeys(listaRegistro.get(identityNumber).getYear());
            car.getButtonPurchase().click();
            if(wait.until(ExpectedConditions.textToBe(By.xpath("//div[@Class='sweet-alert  showSweetAlert visible']//h2"),"Thank you for your purchase!"))){
                Assert.assertEquals(car.getPurchaseAlert().getText(),"Thank you for your purchase!");
                System.out.println(car.getPurchaseData().getText());
                wait.withTimeout(Duration.ofSeconds(10));
                car.getClosePurchase().click();
            }else{
                System.out.println("No salio el dialogo de la compra completada");
            }

            }else{
                System.out.println("no se llego al carrito");
            }
    }

    private WebElement selectedProductDinamic(int n,Home home){
        //System.out.println(home.getProductsTable().findElement(By.xpath("//div[@id='tbodyid']//div["+n+"]//a")).getText());
        int numero = randomGenerateANumber(9);
        if(numero == 0){
            numero =1;
        }
        return home.getProductsTable().findElement(By.xpath("//div[@id='tbodyid']//div["+numero+"]//a"));
    }

    private int randomGenerateANumber(int uperNumber){
        Random rand = new Random();
        int int_random = rand.nextInt(uperNumber);
        return int_random;
    }

    @AfterTest
    private void close() throws InterruptedException {
        wait.withTimeout(Duration.ofSeconds(10));
        driver.quit();
    }
}
