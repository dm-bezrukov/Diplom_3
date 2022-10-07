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

public class LogoutTest {
    WebDriver driver = DriverInitializer.getChromeDriver();
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;

    @Before
    public void init() {
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        driver.get(new ConfigFileReader().getApplicationUrl() + "/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void logoutWithLogoutButtonSuccess() {
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();
        accountPage.clickLogoutButton();
        mainPage.clickAccountButton();

        boolean displayed = loginPage.getSignInButton().isDisplayed();
        Assert.assertTrue("Выход из личного кабинета не был выполнен", displayed);
    }
}
