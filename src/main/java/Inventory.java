import java.util.ArrayList;
import java.util.List;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private List<Product> products = new ArrayList<>();

    private int getProductIndex(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                return i;
            }
        }

        return -1;
    }

    void addProduct(String productId, double price, int quantity) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            Product product = products.get(index);
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }

        } else {
            products.add(new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        boolean exists = false;
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductId() == productId) {
                if(products.get(i).getQuantity() > quantity) {
                    products.get(i).addStock((0 - quantity));
                } else if(products.get(i).getQuantity() == quantity) {
                    products.remove(i);
                } else {
                    throw new InsufficientInventory(
                            products.get(i).getQuantity(), quantity
                    );
                }
                exists = true;
            }
        }
        if(!exists){
            throw new InsufficientInventory(0, quantity);
        }
    }

    Product getProduct(String productId) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            return products.get(index);
        } else {
            return null;
        }
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
        for (int i = 0; i < products.size(); i++) {
            inventorValue += products.get(i).getQuantity() * products.get(i).getPrice();
        }
        return inventorValue;
    }

    boolean inStock(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId && products.get(i).getQuantity() > 0){
                return true;
            }

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
