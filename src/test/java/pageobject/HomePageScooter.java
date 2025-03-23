package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePageScooter {

    private WebDriver driver;

    // Кнопка «Заказать» вверху страницы
    private static final By ORDER_BUTTON_HEADER = By.className("Button_Button__ra12g");

    // Кнопка «Заказать» внизу страницы
    private static final By ORDER_BUTTON_FOOTER = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button");

    public HomePageScooter(WebDriver driver) {
        this.driver = driver;
    }
    // Клик по логотипу «Самокат»
    public void clickScooterLogo() {
        driver.findElement(By.className("Header_LogoScooter__3lsAR")).click();
    }
    public void checkOrderStatus(String orderNumber) {
        driver.findElement(By.xpath("//button[text()='Статус заказа']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Введите номер заказа']")).sendKeys(orderNumber);
        driver.findElement(By.xpath("//button[text()='Go!']")).click();
    }



    // Клик по кнопке «Заказать» (верхняя или нижняя)
    public void clickOrderButton(boolean isHeader) {
        if (isHeader) {
            driver.findElement(ORDER_BUTTON_HEADER).click();
        } else {
            driver.findElement(ORDER_BUTTON_FOOTER).click();
        }
    }
}
