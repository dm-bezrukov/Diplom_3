package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.constants.BrowserEnum;
import ru.practicum.page_objects.*;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

@RunWith(Parameterized.class)
public class LoginTest {
    WebDriver driver;
    MainPage mainPage;
    LoginPage loginPage;
    BrowserEnum browserEnum;
    ConfigFileReader configFileReader = new ConfigFileReader();

    public LoginTest(BrowserEnum browserEnum) {
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
        this.driver = DriverInitializer.getDriver(browserEnum);

        driver.get(configFileReader.getApplicationUrl());
        this.mainPage = new MainPage(driver);
        this.loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    @After
    public void closeDriver() {
        driver.quit();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void signInWithValidDataWithSignInButtonSuccess() {
        mainPage.clickSignInButton();
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getLogoutButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не был выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void signInWithValidDataWithAccountButtonSuccess() {
        mainPage.clickAccountButton();
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getLogoutButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не был выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void signInWithValidDataFromSignUpFormSuccess() {
        mainPage.clickSignInButton();
        loginPage.clickSignUpButton();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.clickSignInButton();
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getLogoutButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не был выполнен", displayed);
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void signInWithValidDataFromPasswordRecoverFormSuccess() {
        mainPage.clickSignInButton();
        loginPage.clickRecoverPasswordButton();
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickSignInButton();
        loginPage.loginWithTestUser();
        mainPage.clickAccountButton();
        AccountPage accountPage = new AccountPage(driver);

        boolean displayed = accountPage.getLogoutButton().isDisplayed();
        Assert.assertTrue("Вход в личный кабинет не был выполнен", displayed);
    }
}