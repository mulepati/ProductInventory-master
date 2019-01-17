import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private Set<Product> products = new HashSet<>();


    void addProduct(String productId, double price, int quantity) {
        Product product = getProduct(productId);
        if(product != null) {
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }

        } else {
            products.add(new Product(productId, price, quantity));
        }

    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {

        Product product = getProduct(productId);
        if(product != null) {
            if(product.getQuantity() > quantity) {
                product.addStock(0-quantity);
            }else if(product.getQuantity() == quantity){
                products.remove(product);
            } else {
                throw new InsufficientInventory (product.getQuantity(), quantity);
            }
        } else {
            throw new InsufficientInventory(0, quantity);
        }
    }

    Product getProduct(String productId) {
        for(Product product: products) {
            if (product.getProductId().equals(productId)){
                return product;
            }
        } return null;
    }

    String getAllProductNames() {
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getProductId());
        }

        return String.join(", ", productIds);
    }

    double totalInventoryValue() {
        double inventorValue = 0;
        for(Product product : products){
            inventorValue += product.getQuantity() * product.getPrice();
        }
        return inventorValue;
    }

    boolean inStock(String productId) {
        Product product = getProduct(productId);
        if(product != null) {
            return product.getQuantity() != 0;
        }

            return false;
    }



    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.getAllProductNames());
    }
}
