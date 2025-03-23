package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.HomePageScooter;
import pageobject.OrderPageScooter;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ScooterOrderTest {

    private WebDriver driver;

    private final String browser;
    private final String name, surname, address, metro, phone, date, duration, comment;

    public ScooterOrderTest(String browser, String name, String surname, String address, String metro, String phone, String date, String duration, String comment) {
        this.browser = browser;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Browser: {0}, Name: {1}")
    public static Object[][] getData() {
        return new Object[][]{
                {"chrome", "Алексей", "Петров", "Ленина 1", "Сокольники", "+79261234567", "24.03.2025", "сутки", "нет"},
                {"firefox", "Мария", "Иванова", "Гагарина 12", "Черкизовская", "+79267654321", "24.03.2025", "четверо суток", "Позвоните заранее"}
        };
    }

    @Before
    public void setup() {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unknown browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void orderScooterPositiveScenarioTest() {
        HomePageScooter homePage = new HomePageScooter(driver);
        homePage.clickOrderButton(true);

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

        assertTrue("Окно с подтверждением заказа не появилось", orderPage.isOrderSuccessPopupVisible());
        assertTrue("Заказ не оформлен!", orderPage.isOrderSuccessMessageDisplayed());
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
