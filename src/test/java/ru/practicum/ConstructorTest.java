package ru.practicum;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.constants.Browser;
import ru.practicum.page_objects.MainPage;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

@RunWith(Parameterized.class)
public class ConstructorTest {
    WebDriver driver;
    MainPage mainPage;
    Browser browserEnum;
    ConfigFileReader configFileReader = new ConfigFileReader();

    public ConstructorTest(Browser browserEnum) {
        this.browserEnum = browserEnum;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {Browser.CHROME},
                {Browser.YANDEX}
        };
    }

    @Before
    public void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        this.driver = DriverInitializer.getDriver(browserEnum);

        driver.get(configFileReader.getApplicationUrl());
        this.mainPage = new MainPage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    public void clickOnBunsSectionButtonAutoScroll() {
        mainPage.clickOnFillingsSectionButton();
        mainPage.clickOnBunsSectionButton();
        String attribute = mainPage.getBunsSectionButton().getAttribute("class");
        Assert.assertEquals("Переход к разделу \"Булки\" не выполнен",
                "tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect", attribute);
    }

    @Test
    public void clickOnSousesSectionButtonAutoScroll() {
        mainPage.clickOnSousesSectionButton();
        String attribute = mainPage.getSousesSectionButton().getAttribute("class");
        Assert.assertEquals("Переход к разделу \"Соусы\" не выполнен",
                "tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect", attribute);
    }

    @Test
    public void clickOnFillingsSectionButtonAutoScroll() {
        mainPage.clickOnFillingsSectionButton();
        String attribute = mainPage.getFillingsSectionButton().getAttribute("class");
        Assert.assertEquals("Переход к разделу \"Начинки\" не выполнен",
                "tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect", attribute);
    }
}