public class Cart {
    private Product[] products;

    public Cart(){
        this.products = new Product[0];
    }

    public Product[] getProducts() {
        return products;
    }

    public Cart(Product[] products) {
        this.products = products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public Product[] addToCart(Product productToAdd){
        Product[] newProducts = new Product[this.products.length + 1];
        for (int i = 0; i < this.products.length; i++) {
            newProducts[i] = this.products[i];
        }
        newProducts[newProducts.length-1] = productToAdd;
        this.products = newProducts;

        return newProducts;
    }

    public double sumPrices(User user){
        Cart cart = ((Client)user).getCart();
        double price = 0;
        for (int i = 0; i < cart.getProducts().length; i++) {
            double productPrice;
            if(((Client) user).isClubMember()){
                productPrice = cart.getProducts()[i].getPriceVIP();
            }
            else {
                productPrice=cart.getProducts()[i].getPrice();
            }
            price += productPrice;
        }
        return price;
    }
}
