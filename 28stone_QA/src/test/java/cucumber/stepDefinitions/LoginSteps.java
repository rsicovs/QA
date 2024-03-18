package cucumber.stepDefinitions;

import cucumber.exceptions.*;
import cucumber.pages.LoginPage;
import cucumber.pages.PurchasePage;
import cucumber.pages.ProductInfo;
import cucumber.pages.CartPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.*;

public class LoginSteps {
    private WebDriver driver;
    WebDriverWait wait;
    static LoginPage loginPage;
    static PurchasePage purchasePage;
    static ProductInfo productInfo;
    static CartPage cartPage;

    public LoginSteps() {
        this.driver = Hooks.driver;
        loginPage = PageFactory.initElements(Hooks.driver, LoginPage.class);
        purchasePage = PageFactory.initElements(Hooks.driver, PurchasePage.class);
        productInfo = PageFactory.initElements(Hooks.driver, ProductInfo.class);
        cartPage = PageFactory.initElements(Hooks.driver, CartPage.class);
    }

    @When("Enter url in browser")
    public void IamOnHomepage() throws Throwable {
        loginPage.navigateToPage(loginPage.getPageUrl());
    }

    @And("All login elements are visible")
    public void allLoginElementsAreVisible() throws LoginVisibilityException {
        assertTrue("All login elements are visible", loginPage.areLoginFieldsVisible());
    }

    @And("^I fill username field: \"([^\"]*)\"$")
    public void iFillUsernameField(String username) {

        loginPage.fillUsername(username);
    }

    @And("^I fill password field: \"([^\"]*)\"$")
    public void iFillPasswordField(String password) {

        loginPage.fillPassword(password);
    }

    @And("I click on Login button")
    public void iClickOnLoginButton() {
        try {
            loginPage.clickLogin();
        } catch (InvalidCredentialsException | UserLockedException | EmptyCredentialsException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    @And("I have successfully logged in")
    public void iHaveSuccessfullyLoggedIn() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("https://www.saucedemo.com/inventory.html"));
    }

    @And("Products are available in the store")
    public void productsAreAvailableInTheStore() throws ProductNotAvailableException {
        try {
            purchasePage.productsAreAvailable();
        } catch (ProductNotAvailableException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    @And("^I add item with \"([^\"]*)\" to the cart")
    public void iAddToTheCart(String productName) throws ProductNotAvailableException {
        purchasePage.ResetAppState(); // reset account in the case if products were added before

        try {
            purchasePage.getInventoryProductInfo(productName);
            purchasePage.AddProductToCart(productName);
        } catch (ProductNotAvailableException e) {
            fail("Failed: " + e.getMessage());
        }
        ProductInfo.InventoryProduct inventoryProduct = purchasePage.getInventoryProductInfo(productName);
    }

    @Then("^I check that the correct item with \"([^\"]*)\" is in the cart")
    public void iCheckThatTheCorrectItemWithIsInTheCart(String productName) throws ProductNotAvailableException {
        cartPage.clickCart();

        ProductInfo.CartProduct cartProduct = cartPage.getCartProductInfo(productName);
        purchasePage.printInventoryProduct(purchasePage.getInventoryProduct());
        cartPage.printInfo(cartProduct);

        //cartPage.compareProducts(purchasePage.getInventoryProduct(), cartProduct);
        assertEquals("Inventory Product and Cart Product do not match.", purchasePage.getInventoryProduct().getProductName().toLowerCase(), cartProduct.getProductName().toLowerCase());
        assertEquals("Inventory Product and Cart Product do not match.", purchasePage.getInventoryProduct().getProductDescription().toLowerCase(), cartProduct.getProductDescription().toLowerCase());
        assertEquals("Inventory Product and Cart Product do not match.", purchasePage.getInventoryProduct().getProductPrice().toLowerCase(), cartProduct.getProductPrice().toLowerCase());
        assertEquals("Inventory Product and Cart Product do not match.", purchasePage.getInventoryProduct().getProductImageUrl().toLowerCase(), cartProduct.getProductImageUrl().toLowerCase());
    }
}
