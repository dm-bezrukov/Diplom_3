package ru.practicum.utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverInitializer {

    public static ChromeDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    public static ChromeDriver getYandexDriver() {
        WebDriverManager.chromedriver().driverVersion("104.0.5112.20").setup();
        return new ChromeDriver(new ChromeOptions().setBinary(new ConfigFileReader().getYandexBrowserBinaryPath()));
    }
}