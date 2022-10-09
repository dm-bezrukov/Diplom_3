package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.constants.BrowserEnum;
import ru.practicum.page_objects.AccountPage;
import ru.practicum.page_objects.LoginPage;
import ru.practicum.page_objects.MainPage;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

@RunWith(Parameterized.class)
public class GoToAccountTest {
    WebDriver driver;
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    BrowserEnum browserEnum;
    ConfigFileReader configFileReader = new ConfigFileReader();

    public GoToAccountTest(BrowserEnum browserEnum) {
        this.browserEnum = browserEnum;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {BrowserEnum.CHROME},
                {BrowserEnum.YANDEX}
        };
    }

    @Before
    public void init() {
        driver = DriverInitializer.getDriver(browserEnum);
        driver.get(configFileReader.getApplicationUrl());
        mainPage = new MainPage(driver);
        accountPage = new AccountPage(driver);
        loginPage = new LoginPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
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

        boolean displayed = accountPage.getProfileButton().isDisplayed();
        Assert.assertTrue("Личный кабинет не был открыт", displayed);
    }
}