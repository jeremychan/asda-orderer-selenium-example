import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AsdaOrderer {

  WebDriver driver;

  public AsdaOrderer() {
    System.setProperty(
      "webdriver.chrome.driver",
      "C:\\dev\\chromedriver_win32\\chromedriver.exe"
    );
    ChromeOptions options = new ChromeOptions();
    options.addArguments(
      "user-data-dir=C:\\dev\\ChromeProfile",
      "profile-directory=Profile 1"
    );
    driver = new ChromeDriver(options);
  }

  public void createOrder(List<String> items) {
    loadPage();

    items.forEach(item -> {
      searchItem(item);
      addFirstResultToShoppingCart(item);
    });
  }

  private void addFirstResultToShoppingCart(String item) {
    var addButtonLocator = By.className("co-quantity__add-btn");
    new WebDriverWait(driver, 10).until(
      ExpectedConditions.elementToBeClickable(addButtonLocator)
    );
    var addButtons = driver.findElements(addButtonLocator);
    addButtons.get(0).click();
  }

  private void searchItem(String item) {
    var inputField = driver.findElement(By.xpath("//*[@id=\"search\"]"));
    inputField.sendKeys(item);
    var searchButton = driver.findElement(
      By.xpath("//*[@id=\"root\"]/div[2]/header/div/div/div/div[3]/div/form/button")
    );
    searchButton.click();
  }

  private void loadPage() {
    driver.get("https://groceries.asda.com/");

    new WebDriverWait(driver, 10).until(
      ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//*[@id=\"search\"]")
      )
    );
  }
}
