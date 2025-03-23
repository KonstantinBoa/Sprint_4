package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPageScooter {
    private final WebDriver driver;

    private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");
    private final By deliveryDateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalDropdown = By.className("Dropdown-control");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By colorBlack = By.id("black");
    private final By finalOrderButton = By.xpath("//button[text()='Заказать']");
    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    private final By successPopup = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    public OrderPageScooter(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstName(String name) {
        driver.findElement(firstNameField).sendKeys(name);
    }

    public void setLastName(String surname) {
        driver.findElement(lastNameField).sendKeys(surname);
    }

    public void setAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void setMetroStation(String station) {
        WebElement metroInput = driver.findElement(metroStationField);
        metroInput.sendKeys(station);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='" + station + "']"))).click();
    }

    public void setPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void setDeliveryDate(String date) {
        WebElement dateInput = driver.findElement(deliveryDateField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateInput);
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER); // нужно для Firefox
    }

    public void setRentalDuration(String durationText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement dropdown = driver.findElement(rentalDropdown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='Dropdown-option' and text()='" + durationText + "']")));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void setScooterColorBlack() {
        driver.findElement(colorBlack).click();
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderFinalButton() {
        driver.findElement(finalOrderButton).click();
    }

    public void clickConfirmOrderButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    public boolean isOrderSuccessPopupVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
    }

    public boolean isOrderSuccessMessageDisplayed() {
        return driver.findElement(successPopup).isDisplayed();
    }
}
