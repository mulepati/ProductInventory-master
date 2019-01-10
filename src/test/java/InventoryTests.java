import org.junit.Assert;
import org.junit.Test;

public class InventoryTests {
    @Test
    public void addApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(2, inventory.getProduct("apple").getQuantity());
    }

    @Test
    public void addMoreApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(3, inventory.getProduct("apple").getQuantity());
    }

    @Test
    public void updatePrice() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .7, 2);

        Assert.assertEquals(.7, inventory.getProduct("apple").getPrice(), .0001);
    }

    @Test(expected = InsufficientInventory.class)
    public void removeApples() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.removeProduct("apple", 12);
    }
    //Test that if you have 10 apples in inventory and 2 are removed, there are 8 left.
    @Test
    public void removeApples2() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.removeProduct("apple", 2);

        Assert.assertEquals(8, inventory.getProduct("apple").getQuantity());
    }

    //Test that if you remove 10 "fakeProduct" (where fakeProduct has not been added to the inventory), an exception is thrown.
    @Test (expected = InsufficientInventory.class)
    public void removeFakeProduct() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.removeProduct("fakeProduct", 10);
    }

    //Test that if you have 10 apples in inventory, inStock("apple") returns true.
    @Test
    public void checkApple() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        Assert.assertEquals(true, inventory.inStock("apple"));

    }

    //Test that if you have 0 apples in inventory, inStock("apple") returns false.
    @Test
    public void checkApple2() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 0);
        Assert.assertEquals(false, inventory.inStock("apple"));
    }
    //Test that if you don't have fakeProduct in the inventory list, inStock("fakeProduct") returns false.
    @Test
    public void checkFakeProduct() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        Assert.assertEquals(false, inventory.inStock("fakeProduct"));
    }

    //Add some products to inventory and test that the result of totalInventoryValue() is correct.
    @Test
    public void addGuns() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("gun", 100, 1);
        inventory.addProduct("gun", 100, 1);

        Assert.assertEquals(2, inventory.getProduct("gun").getQuantity());
    }
}
