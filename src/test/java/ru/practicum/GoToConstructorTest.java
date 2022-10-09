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
public class GoToConstructorTest {
    WebDriver driver;
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
    BrowserEnum browserEnum;

    public GoToConstructorTest(BrowserEnum browserEnum) {
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

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        driver.get(new ConfigFileReader().getApplicationUrl());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешный переход в конструктор из аккаунта")
    public void goToConstructorFromAccountSuccess() {
        mainPage.clickAccountButton();
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();
        accountPage.clickGoToConstructorButton();

        boolean displayed = mainPage.getBurgerConstructorHeader().isDisplayed();
        Assert.assertTrue("Конструктор не был открыт",displayed);
    }
}
