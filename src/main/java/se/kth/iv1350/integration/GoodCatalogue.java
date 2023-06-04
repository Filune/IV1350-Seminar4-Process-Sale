package se.kth.iv1350.integration;

import java.sql.SQLException;
import java.util.HashMap;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.utility.Amount;

/**
 * A singleton catalogue of goods available in the store.
 */
public class GoodCatalogue {
    private static final GoodCatalogue INSTANCE = new GoodCatalogue();;
    private HashMap<String, GoodDTO> goodsDatabase = new HashMap<>();

    private GoodCatalogue() {
        addNewItems();
    }

    /**
     * Retrieves the singleton instance of GoodCatalogue.
     *
     * @return The GoodCatalogue instance.
     */
    public static GoodCatalogue getInstance() {
        return INSTANCE;
    }

    private void addNewItems() {
        addGoodDTOToDB("Apple", 30, 2);
        addGoodDTOToDB("Hamburger", 65, 10);
        addGoodDTOToDB("Cucumber", 10, 2);
        addGoodDTOToDB("Milk", 25, 10);
    }

    private void addGoodDTOToDB(String name, int price, int tax) {
        goodsDatabase.put(name, new GoodDTO(name, new Amount(price), new Amount(tax)));
    }

    /**
     * Checks if a good exists in the catalogue.
     *
     * @param good The name of the good to check for.
     * @return {@code true} if the good exists in the catalog, {@code false} otherwise.
     */
    public boolean containsGood(String good) {
        return goodsDatabase.containsKey(good);
    }

    /**
     * Retrieves a good from the catalog.
     *
     * @param name     The name of the good to retrieve.
     * @param quantity The quantity of the good to retrieve.
     * @return The Good instance corresponding to the specified name and quantity, or {@code null}
     * if the good does not exist in the catalog.
     * @throws InvalidGoodException Thrown if good doesn't exist in the inventory.
     * @throws SQLException        Thrown if the database is offline.
     */
    public Good getGood(String name, Amount quantity) throws InvalidGoodException, SQLException {

        if (name == null) {
            throw new InvalidGoodException("Invalid scanned good.", name, quantity);
        }

        if (name.equals("dbOffline")) {
            throw new SQLException("Database is offline.");
        }

        if (!containsGood(name)) {
            throw new InvalidGoodException("Invalid scanned good.", name, quantity);
        }

        return new Good(goodsDatabase.get(name), name, quantity);
    }
}