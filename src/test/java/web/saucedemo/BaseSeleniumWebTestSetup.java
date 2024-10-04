package web.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseSeleniumWebTestSetup {
    protected WebDriver browser;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }

    protected void openPage(String url) {
        browser.get(url);
    }

    @AfterEach
    public void teardown() {
        if (browser != null) {
            browser.quit();
        }
    }

    // Método para obter captura de tela
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] captureScreenshot() {
        return ((TakesScreenshot) browser).getScreenshotAs(OutputType.BYTES);
    }
}

