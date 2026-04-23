import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

interface Product {
    void displayDetails();
}

class LegacyItem {
    private final int itemId;
    private final String description;

    public LegacyItem(int itemId, String description) {
        this.itemId = itemId;
        this.description = description;
    }

    public void print() {
        System.out.println("Legacy Item");
        System.out.println("Item ID: " + itemId);
        System.out.println("Description: " + description);
    }
}

class ProductAdapter implements Product {
    private final LegacyItem legacyItem;

    public ProductAdapter(LegacyItem legacyItem) {
        this.legacyItem = legacyItem;
    }

    @Override
    public void displayDetails() {
        legacyItem.print();
    }
}

class NewProduct implements Product {
    private final String name;

    public NewProduct(String name) {
        this.name = name;
    }

    @Override
    public void displayDetails() {
        System.out.println("New Product");
        System.out.println("Name: " + name);
    }
}

class InventoryManager {
    private static InventoryManager instance;
    private final List<Product> inventory;

    private InventoryManager() {
        inventory = new ArrayList<>();
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public Iterator<Product> returnInventory() {
        return inventory.iterator();
    }
}

class InventoryManagerApp {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();

        manager.addProduct(new NewProduct("Laptop"));
        manager.addProduct(new ProductAdapter(new LegacyItem(101, "Wireless Mouse")));
        manager.addProduct(new NewProduct("Keyboard"));
        manager.addProduct(new ProductAdapter(new LegacyItem(102, "USB Flash Drive")));

        Iterator<Product> iterator = manager.returnInventory();

        System.out.println("Inventory Details");
        System.out.println("-----------------");
        while (iterator.hasNext()) {
            Product product = iterator.next();
            product.displayDetails();
            System.out.println();
        }
    }
}
