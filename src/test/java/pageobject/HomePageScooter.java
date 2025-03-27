package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageScooter {
    private WebDriver driver;

    // Кнопки «Заказать»
    private final By headerOrderButton = By.xpath("//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");
    private final By footerOrderButton = By.xpath("//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    // Cookie-баннер
    private final By cookieBanner = By.className("App_CookieConsent__1yUIN");
    private final By cookieCloseButton = By.className("App_CookieButton__3cvqF");

    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOrderButton(boolean isHeader) {
        By locator = isHeader ? headerOrderButton : footerOrderButton;
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    public void closeCookieBannerIfPresent() {
        List<WebElement> banners = driver.findElements(cookieBanner);
        if (!banners.isEmpty()) {
            try {
                WebElement close = driver.findElement(cookieCloseButton);
                close.click();
                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.invisibilityOf(banners.get(0)));
            } catch (Exception ignored) {}
        }
    }

    public void clickScooterLogo() {
        driver.findElement(By.className("Header_LogoScooter__3lsAR")).click();
    }

    public void checkOrderStatus(String orderNumber) {
        driver.findElement(By.xpath("//button[text()='Статус заказа']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Введите номер заказа']")).sendKeys(orderNumber);
        driver.findElement(By.xpath("//button[text()='Go!']")).click();
    }
}
