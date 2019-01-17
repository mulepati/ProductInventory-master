
import java.util.HashMap;
import java.util.Map;


class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private Map<String, Product> products = new HashMap<>();


    void addProduct(String productId, double price, int quantity) {
        Product product = products.get(productId);
        if(product != null) {
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }

        } else {
            products.put(productId, new Product(productId, price, quantity));
        }

    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {

        Product product = products.get(productId);
        if(product != null) {
            if(product.getQuantity() > quantity) {
                product.addStock(0-quantity);
            }else if(product.getQuantity() == quantity){
                products.remove(productId);
            } else {
                throw new InsufficientInventory (product.getQuantity(), quantity);
            }
        } else {
            throw new InsufficientInventory(0, quantity);
        }
    }


    String getAllProductNames() {

        return String.join(", ", products.keySet());
    }

    double totalInventoryValue() {
        double inventorValue = 0;
        for(Product product : products.values()){
            inventorValue += product.getQuantity() * product.getPrice();
        }
        return inventorValue;
    }

    boolean inStock(String productId) {
        Product product = products.get(productId);
        if(product != null) {
            return product.getQuantity() != 0;
        }

            return false;
    }

    Product getProduct(String productId) {
        return products.get(productId);
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.getAllProductNames());
    }
}
