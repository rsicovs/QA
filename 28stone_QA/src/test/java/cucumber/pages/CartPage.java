package cucumber.pages;

import cucumber.exceptions.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import java.util.List;

import static cucumber.stepDefinitions.Hooks.driver;

public class CartPage {
    @FindBy(css = ".shopping_cart_link")
    private WebElement cartButton;
    @FindBy(css = ".cart_item")
    private List<WebElement> cartProductList;
    private ProductInfo.CartProduct cartProduct;
    public void clickCart() {
        cartButton.click();
    }
    public ProductInfo.CartProduct getCartProduct() {
        return cartProduct;
    }
    public ProductInfo.CartProduct getCartProductInfo(String productName) throws ProductNotAvailableException {
        try {
            // Find productName in product list
            for (WebElement product : cartProductList) {
                String productNameOnPage = product.findElement(By.cssSelector(".inventory_item_name")).getText();
                if (productNameOnPage.equalsIgnoreCase(productName)) {
                    String description = product.findElement(By.cssSelector(".inventory_item_desc")).getText();
                    String price = product.findElement(By.cssSelector(".inventory_item_price")).getText();

                    cartProduct = new ProductInfo.CartProduct(productName, description, price, "");
                    setProductImageUrlByClickingProductName(productName);

                    return cartProduct;
                }
            }
            throw new ProductNotAvailableException("Product not found in cart product list: " + productName);
        } catch (NoSuchElementException e) {
            throw new ProductNotAvailableException("Error occurred while retrieving product information: " + e.getMessage());
        }
    }
    public void printInfo(ProductInfo.CartProduct cartProduct) {
        System.out.println("Product Name: " + cartProduct.getProductName());
        System.out.println("Description: " + cartProduct.getProductDescription());
        System.out.println("Price: " + cartProduct.getProductPrice());
        System.out.println("Image URL: " + cartProduct.getProductImageUrl());
    }
    public void setProductImageUrlByClickingProductName(String productName) throws ProductNotAvailableException {
        try {
            // Find the product in the cart by its name and click on it to view its details
            for (WebElement product : cartProductList) {
                String productNameOnPage = product.findElement(By.cssSelector(".inventory_item_name")).getText();
                if (productNameOnPage.equalsIgnoreCase(productName)) {
                    product.findElement(By.cssSelector(".inventory_item_name")).click();
                    String imageUrl = driver.findElement(By.cssSelector(".inventory_details_img")).getAttribute("src");
                    // Set the image URL for the cart product
                    cartProduct.setProductImageUrl(imageUrl);
                    driver.navigate().back();
                    return;
                }
            }
            throw new ProductNotAvailableException("Product not found in cart: " + productName);
        } catch (NoSuchElementException e) {
            throw new ProductNotAvailableException("Error occurred while retrieving product information: " + e.getMessage());
        }
    }
}
