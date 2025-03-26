// ScooterOrderTest.java (очищенный от дубликатов)
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobject.HomePageScooter;
import pageobject.OrderPageScooter;

import java.time.Duration;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ScooterOrderTest {
    private WebDriver driver;
    private final String browser;

    public ScooterOrderTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Object[] data() {
        return new Object[]{"chrome", "firefox"};
    }

    @Before
    public void setUp() {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testScooterLogoRedirectsToHomePage() {
        driver.get("https://qa-scooter.praktikum-services.ru/order");
        HomePageScooter homePageScooter = new HomePageScooter(driver);
        homePageScooter.clickScooterLogo();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.urlContains("https://qa-scooter.praktikum-services.ru/"));
        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

    @Test
    public void testYandexLogoOpensNewWindow() {
        driver.findElement(By.xpath("//img[@alt='Yandex']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);

        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("dzen.ru"));

        assertTrue(driver.getCurrentUrl().contains("dzen.ru"));
    }

    @Test
    public void testOrderFormEmptyFieldsValidation() {
        HomePageScooter homePage = new HomePageScooter(driver);
        homePage.clickOrderButton(true);
        OrderPageScooter orderPage = new OrderPageScooter(driver);
        orderPage.clickNextButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        assertTrue("Ошибка валидации имени не отображается",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[text()='Введите корректное имя']"))).isDisplayed());

        assertTrue("Ошибка валидации фамилии не отображается",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[text()='Введите корректную фамилию']"))).isDisplayed());

        assertTrue("Ошибка валидации метро не отображается",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[text()='Выберите станцию']"))).isDisplayed());

        assertTrue("Ошибка валидации телефона не отображается",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[text()='Введите корректный номер']"))).isDisplayed());
    }

    @Test
    public void testInvalidOrderNumberShowsNotFound() {
        HomePageScooter homePageScooter = new HomePageScooter(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.xpath("//button[text()='Статус заказа']")).click();

        WebElement orderInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Введите номер заказа']")));
        orderInput.sendKeys("123456789");

        driver.findElement(By.xpath("//button[text()='Go!']")).click();

        assertTrue("Сообщение 'Заказ не найден' не отображается",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Not found']"))).isDisplayed());
    }
}
