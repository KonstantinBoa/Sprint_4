package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class OrderPageScooter {

    private WebDriver driver;

    private By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    private By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath(".//button[text()='Далее']");
    private By deliveryDateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalDurationDropdown = By.className("Dropdown-control");
    private By scooterColorBlackCheckbox = By.id("black");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderFinalButton = By.xpath("//button[text()='Заказать']");
    private By confirmOrderButton = By.xpath("//button[text()='Да']");
    private By successOrderPopup = By.xpath("//div[contains(text(),'Заказ оформлен')]");

    public OrderPageScooter(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void setAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void setMetroStation(String metro) {
        driver.findElement(metroStationField).sendKeys(metro);
        driver.findElement(By.xpath("//div[text()='" + metro + "']")).click();
    }

    public void setPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void setDeliveryDate(String date) {
        driver.findElement(deliveryDateField).sendKeys(date);
    }

    public void setRentalDuration(String duration) {
        driver.findElement(rentalDurationDropdown).click();
        driver.findElement(By.xpath("//div[text()='" + duration + "']")).click();
    }

    public void setScooterColorBlack() {
        driver.findElement(scooterColorBlackCheckbox).click();
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderFinalButton() {
        driver.findElement(orderFinalButton).click();
    }

    public void clickConfirmOrderButton() {
        driver.findElement(confirmOrderButton).click();
    }

    public boolean isOrderSuccessPopupVisible() {
        return driver.findElement(successOrderPopup).isDisplayed();
    }

    public String getSuccessMessage() {
        return driver.findElement(By.className("Order_ModalHeader__3FDaJ")).getText();
    }
    // проверка, что заказ успешно оформлен
    public boolean isOrderSuccessMessageDisplayed() {
        return driver.findElement(By.xpath("//div[contains(text(),'Заказ оформлен')]")).isDisplayed();
    }
    public void checkOrderStatus(String orderNumber) {
        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Введите номер заказа']"));
        input.click();
        input.sendKeys(orderNumber);
        driver.findElement(By.xpath("//button[text()='Go!']")).click();

    }


}
