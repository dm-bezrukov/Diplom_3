package ru.practicum;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.practicum.page_objects.LoginPage;
import ru.practicum.page_objects.MainPage;
import ru.practicum.page_objects.SignUpPage;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

public class SignUpTest {
    WebDriver driver = DriverInitializer.getChromeDriver();
    MainPage mainPage;
    LoginPage loginPage;
    SignUpPage signUpPage;

    @Before
    public void init() {
        driver.get(new ConfigFileReader().getApplicationUrl());
        this.mainPage = new MainPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @DisplayName("Успешная регистрация с корректными данными")
    @Test
    public void signUpWithValidDataSuccess() {
        mainPage.clickSignInButton();
        loginPage = new LoginPage(driver);
        loginPage.clickSignUpButton();
        signUpPage = new SignUpPage(driver);
        signUpPage.enterName(RandomStringUtils.randomAlphabetic(10));
        signUpPage.enterEmail(RandomStringUtils.randomAlphanumeric(10, 12) + "@test.com");
        signUpPage.enterPassword(RandomStringUtils.randomAlphanumeric(6, 12));
        signUpPage.clickSignUpButton();

        boolean displayed = loginPage.getSignInButton().isDisplayed();
        Assert.assertTrue("Регистрация не была выполнена", displayed);
    }

    @DisplayName("Ошибка при регистрации - пароль меньше 6 символов")
    @Test
    public void signUpWithInvalidPasswordFails() {
        mainPage.clickSignInButton();
        loginPage = new LoginPage(driver);
        loginPage.clickSignUpButton();
        signUpPage = new SignUpPage(driver);
        signUpPage.enterName(RandomStringUtils.randomAlphabetic(10));
        signUpPage.enterEmail(RandomStringUtils.randomAlphanumeric(10, 12) + "@test.com");
        signUpPage.enterPassword(RandomStringUtils.randomAlphanumeric(5));
        signUpPage.clickSignUpButton();

        boolean displayed = signUpPage.getPasswordErrorMessage().isDisplayed();
        Assert.assertTrue("Не была выведена ошибка о некорректном пароле", displayed);
    }
}