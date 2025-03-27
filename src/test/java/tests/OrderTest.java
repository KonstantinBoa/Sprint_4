// OrderTest.java (финальная версия с обработкой куков и параметром isHeaderButton)
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.HomePageScooter;
import pageobject.OrderPageScooter;

import java.time.Duration;

import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    private final String browser;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String duration;
    private final String comment;
    private final boolean isHeaderButton; // <-- добавлено

    public OrderTest(String browser, String name, String surname, String address, String metro,
                     String phone, String date, String duration, String comment, boolean isHeaderButton) {
        this.browser = browser;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
        this.isHeaderButton = isHeaderButton; // <-- добавлено
    }

    @Parameterized.Parameters(name = "Browser: {0}, Кнопка сверху: {9}")
    public static Object[][] getData() {
        return new Object[][]{
                {"chrome", "Алексей", "Петров", "Ленина 1", "Сокольники", "+79261234567", "28.03.2025", "сутки", "коммент 1", true},
                {"chrome", "Алексей", "Петров", "Ленина 1", "Сокольники", "+79261234567", "28.03.2025", "сутки", "коммент 1", false},
                {"firefox", "Мария", "Иванова", "Гагарина 12", "Черкизовская", "+79267654321", "28.03.2025", "двое суток", "коммент 2", true},
                {"firefox", "Мария", "Иванова", "Гагарина 12", "Черкизовская", "+79267654321", "28.03.2025", "двое суток", "коммент 2", false},
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
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void orderScooterPositiveScenarioTest() {
        HomePageScooter homePage = new HomePageScooter(driver);
        homePage.closeCookieBannerIfPresent(); // <-- добавлено
        homePage.clickOrderButton(isHeaderButton);

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

        assertThat("Проверка оформления заказа",
                orderPage.labelOrderCompleteGetText(), CoreMatchers.containsString("Заказ оформлен"));
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
