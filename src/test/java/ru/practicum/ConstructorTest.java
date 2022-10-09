package ru.practicum;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.practicum.constants.BrowserEnum;
import ru.practicum.page_objects.MainPage;
import ru.practicum.utils.ConfigFileReader;
import ru.practicum.utils.DriverInitializer;

import java.time.Duration;

@RunWith(Parameterized.class)
public class ConstructorTest {
    WebDriver driver;
    MainPage mainPage;
    BrowserEnum browserEnum;
    ConfigFileReader configFileReader = new ConfigFileReader();

    public ConstructorTest(BrowserEnum browserEnum) {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @After
    public void shutdown() {
        driver.quit();
    }

    @Test
    public void clickOnBunsSectionButtonAutoScroll() {
        mainPage.clickOnFillingsSectionButton();
        mainPage.clickOnBunsSectionButton();
        boolean displayed = mainPage.getBunsSectionHeader().isDisplayed();
        Assert.assertTrue("Переход к разделу \"Булки\" не выполнен", displayed);
    }

    @Test
    public void clickOnSousesSectionButtonAutoScroll() {
        mainPage.clickOnSousesSectionButton();
        boolean displayed = mainPage.getSousesSectionButton().isDisplayed();
        Assert.assertTrue("Переход к разделу \"Соусы\" не выполнен", displayed);
    }

    @Test
    public void clickOnFillingsSectionButtonAutoScroll() {
        mainPage.clickOnFillingsSectionButton();
        boolean displayed = mainPage.getFillingsSectionHeader().isDisplayed();
        Assert.assertTrue("Переход к разделу \"Начинки\" не выполнен", displayed);
    }
}