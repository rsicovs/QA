package cucumber.pages;

import cucumber.exceptions.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static cucumber.stepDefinitions.Hooks.driver;

public class LoginPage {
    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public String getPageUrl() {
        return "https://www.saucedemo.com/";
    }

    public void navigateToPage(String url) {
        driver.get(url);
    }

    public void waitForLoginFieldsIsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        wait.until(ExpectedConditions.visibilityOf(loginButton));
    }

    public boolean areLoginFieldsVisible() throws LoginVisibilityException {
        try {
            waitForLoginFieldsIsVisible();

            boolean usernameVisible = usernameInput.isDisplayed();
            boolean passwordVisible = passwordInput.isDisplayed();
            boolean loginButtonVisible = loginButton.isDisplayed();

            if (!usernameVisible || !passwordVisible || !loginButtonVisible) {
                StringBuilder errorMessage = new StringBuilder("The following login elements are not visible: ");
                if (!usernameVisible) {
                    errorMessage.append("Username input");
                }
                if (!passwordVisible) {
                    errorMessage.append("Password input");
                }
                if (!loginButtonVisible) {
                    errorMessage.append("Login button");
                }
                throw new LoginVisibilityException(errorMessage.toString());
            }
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            throw new LoginVisibilityException("One or more login elements are not found!");
        }
    }

    public void fillUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void fillPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() throws InvalidCredentialsException, UserLockedException, EmptyCredentialsException {
        loginButton.click();
        // Check for other error messages only if the URL hasn't redirected
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("https://www.saucedemo.com/inventory.html")) {
            WebElement errorElement = driver.findElement(By.cssSelector("[data-test='error']"));
            String errorMessage = errorElement.getText();
            if (errorMessage.contains("Username and password do not match any user")) {
                throw new InvalidCredentialsException("Wrong username or password. Please try again.");
            } else if (errorMessage.contains("Sorry, this user has been locked out")) {
                throw new UserLockedException("This user has been locked.");
            } else if (errorMessage.contains("Username is required")) {
                throw new EmptyCredentialsException("Login fields are empty. Please enter username and password.");
            } else System.out.println("Password or Login is empty.");
        }
    }
}
