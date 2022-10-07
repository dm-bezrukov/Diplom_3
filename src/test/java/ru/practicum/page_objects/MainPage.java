package ru.practicum.page_objects;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class MainPage {
    WebDriver driver;
    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    WebElement homeSignInButton;
    @FindBy(xpath = "//p[text()='Личный Кабинет']")
    WebElement homeAccountButton;
    @FindBy(className = "BurgerIngredients_ingredients__1N8v2")
    WebElement burgerIngredientsSection;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickAccountButton() {
        homeAccountButton.click();
    }

    public void clickSignInButton() {
        homeSignInButton.click();
    }
}