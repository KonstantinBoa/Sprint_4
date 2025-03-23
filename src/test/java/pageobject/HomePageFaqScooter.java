package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePageFaqScooter {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePageFaqScooter(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Клик по вопросу по индексу
    public void clickQuestion(int index) {
        By questionLocator = By.id("accordion__heading-" + index);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    // Получить текст ответа по индексу
    public String getAnswerText(int index) {
        By answerLocator = By.id("accordion__panel-" + index);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator)).getText();
    }
}
