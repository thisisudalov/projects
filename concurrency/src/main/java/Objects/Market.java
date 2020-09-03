package Objects;

import java.util.concurrent.locks.ReentrantLock;

public class Market {
    private static int itemsAmount = 1000;

    public static synchronized int takeItems(int itemsToTake) {
        if (itemsAmount >= itemsToTake) {
            itemsAmount -= itemsToTake;
            return itemsToTake;
        } else {
            int lastedItems = itemsAmount;
            itemsAmount = 0;
            return lastedItems;
        }
    }

    public static int getItemsAmount() {
        return itemsAmount;
    }
}
