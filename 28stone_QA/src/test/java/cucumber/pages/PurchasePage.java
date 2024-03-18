package cucumber.pages;

import cucumber.exceptions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static cucumber.stepDefinitions.Hooks.driver;

import org.openqa.selenium.NoSuchElementException;
public class PurchasePage {
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;

    @FindBy(css = ".inventory_list")
    private WebElement productListContainer;

    @FindBy(css = ".inventory_item")
    private List<WebElement> productList;

    private ProductInfo.InventoryProduct inventoryProduct;

    public void ResetAppState() {
        menuButton.click();
        resetAppStateLink.click();
        driver.navigate().refresh(); // Reload the page (in the case if product was added before, "Remove" button is visible instead of "Add to cart")
    }

    public void productsAreAvailable() throws ProductNotAvailableException {
        // check that products are added to store, if not catch exception
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(productListContainer));

            if (productList.isEmpty()) {
                throw new ProductNotAvailableException("Products are not added to the store");
            }
            } catch (NoSuchElementException | StaleElementReferenceException | ProductNotAvailableException e) {
            System.out.println("Error occurred while checking product availability: " + e.getMessage());
        }
    }

    public ProductInfo.InventoryProduct getInventoryProduct() {
        return inventoryProduct;
    }

    public void setInventoryProduct(ProductInfo.InventoryProduct inventoryProduct) {
        this.inventoryProduct = inventoryProduct;
    }

    public ProductInfo.InventoryProduct getInventoryProductInfo(String productName) throws ProductNotAvailableException {
        try {
            // Find productName in product list
            for (WebElement product : productList) {
                String productNameOnPage = product.findElement(By.cssSelector(".inventory_item_name")).getText();
                if (productNameOnPage.equalsIgnoreCase(productName)) {
                    String description = product.findElement(By.cssSelector(".inventory_item_desc")).getText();
                    String price = product.findElement(By.cssSelector(".inventory_item_price")).getText();
                    String imageUrl = product.findElement(By.tagName("img")).getAttribute("src");

                    ProductInfo.InventoryProduct inventoryProduct = new ProductInfo.InventoryProduct(productName, description, price, imageUrl);

                    setInventoryProduct(inventoryProduct);

                    return inventoryProduct;
                }
            }
            throw new ProductNotAvailableException("Product not found in product list: " + productName);
        } catch (NoSuchElementException e) {
            throw new ProductNotAvailableException("Error occurred while retrieving product information: " + e.getMessage());
        }
    }

    public void AddProductToCart(String productName) throws ProductNotAvailableException {
        try {
            for (WebElement product : productList) {
                String productNameOnPage = product.findElement(By.cssSelector(".inventory_item_name")).getText();
                if (productNameOnPage.equals(productName)) {
                    WebElement addToCartButton = product.findElement(By.cssSelector(".btn_inventory"));
                    addToCartButton.click();
                    return;
                }
            }
            throw new ProductNotAvailableException("Product not found in the inventory: " + productName);
        } catch (NoSuchElementException e) {
            throw new ProductNotAvailableException("Error occurred while adding product to cart: " + e.getMessage());
        }
    }

    public void printInventoryProduct(ProductInfo.InventoryProduct inventoryProduct) {
        System.out.println("Product Name Inv: " + inventoryProduct.getProductName());
        System.out.println("Description Inv: " + inventoryProduct.getProductDescription());
        System.out.println("Price Inv: " + inventoryProduct.getProductPrice());
        System.out.println("Image URL: " + inventoryProduct.getProductImageUrl());
    }
}


