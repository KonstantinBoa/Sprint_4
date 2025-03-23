package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobject.HomePageScooter;
import pageobject.OrderPageScooter;


import java.time.Duration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class OrderTest {

    private WebDriver driver;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testOrderScooter() {
        HomePageScooter homePage = new HomePageScooter(driver);
        homePage.clickOrderButton(true);
    }

    @Test
    public void testScooterLogoRedirectsToHomePage() {
        driver.get("https://qa-scooter.praktikum-services.ru/order");
        HomePageScooter homePageScooter = new HomePageScooter(driver);
        homePageScooter.clickScooterLogo();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(urlContains("https://qa-scooter.praktikum-services.ru/"));
        assertEquals("https://qa-scooter.praktikum-services.ru/", driver.getCurrentUrl());
    }

    @Test
    public void testYandexLogoOpensNewWindow() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        driver.findElement(By.xpath("//img[@alt='Yandex']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.getWindowHandles().size() > 1);

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

        // Явно открываем форму статуса заказа!
        driver.findElement(By.xpath("//button[text()='Статус заказа']")).click();

        // Ждём пока поле станет кликабельным
        WebElement orderInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Введите номер заказа']")));
        orderInput.sendKeys("123456789");

        // Нажимаем кнопку Go!
        driver.findElement(By.xpath("//button[text()='Go!']")).click();

        // Ждем появления картинки Not found
        assertTrue("Сообщение 'Заказ не найден' не отображается",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Not found']"))).isDisplayed());
    }



    @After
    public void teardown() {
        driver.quit();
    }
}
