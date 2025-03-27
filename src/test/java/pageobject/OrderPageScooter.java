// OrderPageScooter.java (обновлённый с учетом прожатия кнопок)
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
    private final By scooterColorBlackCheckbox = By.id("black");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By buttonToOrder = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final By confirmOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    private final By successPopup = By.xpath("//div[contains(text(),'Заказ оформлен')]");
    private final By calendarOverlay = By.className("react-datepicker__month");

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

    public void setMetroStation(String metro) {
        WebElement input = driver.findElement(metroStationField);
        input.sendKeys(metro);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + metro + "']"))).click();
    }

    public void setPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void setDeliveryDate(String date) {
        WebElement input = driver.findElement(deliveryDateField);
        input.click();
        input.sendKeys(date);
        input.sendKeys(Keys.ESCAPE);
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.invisibilityOfElementLocated(calendarOverlay));
    }

    public void setRentalDuration(String durationText) {
        WebElement dropdown = driver.findElement(rentalDropdown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        WebElement option = new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Dropdown-option' and text()='" + durationText + "']")));
        option.click();
    }

    public void setScooterColorBlack() {
        driver.findElement(scooterColorBlackCheckbox).click();
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void removeDatePickerOverlay() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove()",
                    driver.findElement(By.className("react-datepicker__month")));
        } catch (NoSuchElementException ignored) {}
    }

    public void clickOrderFinalButton() {
        removeDatePickerOverlay();
        WebElement button = driver.findElement(buttonToOrder);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    public void clickConfirmOrderButton() {
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        button.click();
    }

    public boolean isOrderSuccessPopupVisible() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
    }

    public boolean isOrderSuccessMessageDisplayed() {
        return driver.findElement(successPopup).isDisplayed();
    }

    public String labelOrderCompleteGetText() {
        return driver.findElement(successPopup).getText();
    }
}
