package com.searchtest.webdriver;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LogInPage;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static pages.Name.TestBrowser;

/**
 * Created by PS on 4/4/2017.
 */
public class TestBaseMain {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;
    public String browserName;
    LogInPage LogInPage;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        switch (TestBrowser.getFromProperty()) {
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver2_28.exe");
                caps.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
                driver = new ChromeDriver(caps);
                tlDriver.set(driver);
                wait = new WebDriverWait(driver, 10);
                break;
            case "FirefoxOld":
                caps.setCapability(FirefoxDriver.MARIONETTE, false);
                driver = new FirefoxDriver(caps);
                tlDriver.set(driver);
                break;
            case "FirefoxNightly":
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                caps.setCapability(FirefoxDriver.MARIONETTE, true);
                driver = new FirefoxDriver(new FirefoxBinary(
                        new File("C:\\Program Files\\Nightly\\firefox.exe")),
                        new FirefoxProfile(),caps);
                tlDriver.set(driver);
                break;
            case "IE":
                System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
                caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                driver = new InternetExplorerDriver(caps);
                tlDriver.set(driver);
                break;
        }
//        driver.manage().deleteAllCookies();
        driver.manage().window().setSize(new Dimension(1280,1024));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));

        LogInPage = PageFactory.initElements(driver, LogInPage.class);

    }

    @After
    public void stop() {
//        driver.quit();
//        driver = null;
    }

    public void waitIsVisible(WebElement element, By locator){

        waitIsVisible(element, locator);


    }
}

