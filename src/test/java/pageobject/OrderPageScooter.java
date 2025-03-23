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
    private final By orderFinalButton = By.xpath("//button[text()='Заказать']");
    private final By confirmOrderButton = By.xpath("//button[text()='Да']");
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + metro + "']"))).click();
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
        input.sendKeys(Keys.ESCAPE); // Закрываем календарь
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.invisibilityOfElementLocated(calendarOverlay));
    }

    public void setRentalDuration(String durationText) {
        WebElement dropdown = driver.findElement(rentalDropdown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='Dropdown-option' and text()='" + durationText + "']")));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void setScooterColorBlack() {
        driver.findElement(scooterColorBlackCheckbox).click();
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderFinalButton() {
        WebElement button = driver.findElement(orderFinalButton);

               ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(button));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'Order_Modal__')]")));
    }



    public void clickConfirmOrderButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }


    public boolean isOrderSuccessMessageDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
    }
}
