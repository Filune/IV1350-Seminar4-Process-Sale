package se.kth.iv1350.integration;

import java.util.HashMap;
import java.util.Map;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.model.Sale;
import se.kth.iv1350.utility.Amount;

/**
 * Represents an external inventory system that interacts with a goods catalogue (database).
 */
public class InventoryManager {

    private HashMap<String, Good> goodsInventory = new HashMap<>();
    private GoodCatalogue goodCatalogue;
    private int goodsAmount = 0;

    /**
     * Initialises the goods catalogue and adds new items to the inventory.
     */
    InventoryManager() {
        addNewItems();
        goodCatalogue = GoodCatalogue.getInstance();
    }

    /**
     * Returns the good catalogue.
     * 
     * @return The good catalogue.
     */
    public GoodCatalogue getGoodCatalogue() {
        return goodCatalogue;
    }

    private boolean goodExistsInGoodsCatalogue(Good nameOfGood) {
        return goodsInventory.containsKey(nameOfGood.getName());
    }

    private void decreaseQuantityOfGood(Good good)  {
        String goodName = good.getName();
        Good existingGoodInDatabase = goodsInventory.get(goodName);
        existingGoodInDatabase.decreaseQuantityOfGood(good.getQuantity());
        goodsInventory.put(goodName, existingGoodInDatabase);    
    }

    /**
     * Updates the goods amount in the external inventory database.
     *
     * @param sale The sale being made.
     * @throws InventoryUpdateException If an error occurs during inventory update.
     */
    public void updateInventory(Sale sale) throws InventoryUpdateException {
        if (sale == null) {         
            throw new IllegalStateException("Sale has to be started before updating the inventory.");     
        }
        
        try {
            HashMap<String, Good> goods = sale.getGoods();
            updateGoodsInInventory(goods);
        } catch (Exception exception) {
            throw new InventoryUpdateException("Error updating inventory: " + exception.getMessage(), exception);
        }
    }

    private void updateGoodsInInventory(HashMap<String, Good> goods) {
        for (Map.Entry<String, Good> entry : goods.entrySet()) {
            Good good = entry.getValue();
    
            if (goodExistsInGoodsCatalogue(good)) {
                decreaseQuantityOfGood(good);
            }
        }
    }

    private void addNewItems() {
        addGoodToDB("Apple", 30, goodsAmount);
        addGoodToDB("Hamburger", 65, goodsAmount);
        addGoodToDB("Cucumber", 10, goodsAmount);
        addGoodToDB("Milk", 25, goodsAmount);
    }
    
    private void addGoodToDB(String name, int price, int quantity) {
        goodsInventory.put(name, new Good(new GoodDTO(name, new Amount(price), new Amount(quantity)), name, new Amount(goodsAmount)));
    }
}
