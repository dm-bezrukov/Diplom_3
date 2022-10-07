package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.page_objects.AccountPage;
import ru.practicum.page_objects.LoginPage;
import ru.practicum.page_objects.MainPage;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

public class GotToAccountTest {
    WebDriver driver = DriverInitializer.getChromeDriver();
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    ConfigFileReader configFileReader = new ConfigFileReader();

    @Before
    public void init() {
        driver.get(configFileReader.getApplicationUrl());
        mainPage = new MainPage(driver);
        accountPage = new AccountPage(driver);
        loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешный переход по клику на «Личный кабинет»")
    public void goToAccountWithAccountButtonSuccess() {
        mainPage.clickAccountButton();
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();

        boolean displayed = accountPage.getLogoutButton().isDisplayed();
        Assert.assertTrue("Личный кабинет не был открыт", displayed);
    }

}
