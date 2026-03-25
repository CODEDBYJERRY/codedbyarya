package demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.URL;

public class GridLoginTest {

    // ThreadLocal ensures each parallel test has its own WebDriver instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Parameters({"browser", "gridUrl"}) 
    @BeforeMethod
    public void setUp(String browser, String gridUrl) throws Exception {

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Opens Chrome maximized
            driver.set(new RemoteWebDriver(new URL(gridUrl), options)); // Connects to Grid

        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            driver.set(new RemoteWebDriver(new URL(gridUrl), options)); // Connects to Grid
        }
    }

    @Test
    public void openHomePageAndVerifyTitle() {
        driver.get().get("https://example.com"); // Open website through Grid

        String title = driver.get().getTitle();
        System.out.println("Page title = " + title);

        Assert.assertTrue(title != null && !title.trim().isEmpty(), "Title is empty!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit(); // Close browser on Node
        }
        driver.remove();
    }
}