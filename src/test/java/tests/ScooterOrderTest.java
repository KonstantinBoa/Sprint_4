package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.HomePageScooter;
import pageobject.OrderPageScooter;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ScooterOrderTest {

    private WebDriver driver;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String duration;
    private final String comment;

    public ScooterOrderTest(String name, String surname, String address, String metro, String phone, String date, String duration, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {"Алексей", "Петров", "Ленина 1", "Сокольники", "+79261234567", "22.03.2025", "сутки", "нет"},
                {"Мария", "Иванова", "Гагарина 12", "Черкизовская", "+79267654321", "23.03.2025", "двое суток", "Позвоните заранее"}
        };
    }

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void orderScooterPositiveScenarioTest() {
        HomePageScooter homePage = new HomePageScooter(driver);
        homePage.clickOrderButton(true); // или false для нижней кнопки

        OrderPageScooter orderPage = new OrderPageScooter(driver);
        orderPage.setFirstName(name);
        orderPage.setLastName(surname);
        orderPage.setAddress(address);
        orderPage.setMetroStation(metro);
        orderPage.setPhone(phone);
        orderPage.clickNextButton();

        orderPage.setDeliveryDate(date);
        orderPage.setRentalDuration(duration);
        orderPage.setScooterColorBlack();
        orderPage.setComment(comment);
        orderPage.clickOrderFinalButton();
        orderPage.clickConfirmOrderButton();

        // Проверка, что заказ оформлен успешно
        assertTrue("Окно с подтверждением заказа не появилось", orderPage.isOrderSuccessPopupVisible());
        assertTrue("Заказ не оформлен!", orderPage.isOrderSuccessMessageDisplayed());
    }



    @After
    public void teardown() {
        driver.quit();
    }
}
