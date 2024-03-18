package cucumber.pages;
public class ProductInfo {
    public static class InventoryProduct {
        private String productName;
        private String productDescription;
        private String productPrice; // not double to check the bugs if currency in the cart will be different then in inventory
        private String productImageUrl;

        public InventoryProduct(String productName, String productDescription, String productPrice, String productImageUrl) {
            this.productName = productName;
            this.productDescription = productDescription;
            this.productPrice = productPrice;
            this.productImageUrl = productImageUrl;
        }
        public String getProductName() {
            return productName;
        }
        public void setProductName(String productName) {
            this.productName = productName;
        }
        public String getProductDescription() {
            return productDescription;
        }
        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }
        public String getProductPrice() {
            return productPrice;
        }
        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }
        public String getProductImageUrl() {
            return productImageUrl;
        }
        public void setProductImageUrl(String productImageUrl) {
            this.productImageUrl = productImageUrl;
        }
    }

    public static class CartProduct {
        private String productName;
        private String productDescription;
        private String productPrice;
        private String productImageUrl;

        public CartProduct(String productName, String productDescription, String productPrice, String productImageUrl) {
            this.productName = productName;
            this.productDescription = productDescription;
            this.productPrice = productPrice;
            this.productImageUrl = productImageUrl;
        }
        public String getProductName() {
            return productName;
        }
        public void setProductName(String productName) {
            this.productName = productName;
        }
        public String getProductDescription() {
            return productDescription;
        }
        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }
        public String getProductPrice() {
            return productPrice;
        }
        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }
        public String getProductImageUrl() {
            return productImageUrl;
        }
        public void setProductImageUrl(String productImageUrl) {
            this.productImageUrl = productImageUrl;
        }
    }
}
