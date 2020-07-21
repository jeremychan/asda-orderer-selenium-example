import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  public void createOrder(List<String> items) {
    loadPage();

    double total = items.stream()
      .map(item -> {
        searchItem(item);
        return getPriceOfFirstResult();
      }).mapToDouble(Double::doubleValue).sum();

    System.out.println(String.format("Total: %.2f", total));
    driver.close();
  }

  private double getPriceOfFirstResult() {
    By priceTagLocator = By.className("co-product__price");
    List<WebElement> priceTags = driver.findElements(priceTagLocator);
    return Double.parseDouble(
      priceTags.get(0).getText().replace("£", "")
    );
  }

  private void searchItem(String item) {
    WebElement inputField = driver.findElement(By.id("search"));
    inputField.sendKeys(item);
    WebElement searchButton = driver.findElement(
      By.className("search-form__search-button")
    );
    searchButton.click();
  }

  private void loadPage() {
    driver.get("https://groceries.asda.com/");
  }
}
