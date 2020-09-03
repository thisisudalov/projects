package Logic;

import Objects.Customer;
import Objects.Market;
import Tasks.MyCallableTask;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PurchaseController {
    int purchaseBound = 1;
    public void makePurchases(List<Customer> customerList, int n) {
        Random random = new Random();
        Set<Customer> setOfCustomers = new HashSet<>();
        ExecutorService executor = Executors.newCachedThreadPool();

        while (Market.getItemsAmount() > 0) {
            Customer calledCustomer = customerList.get(random.nextInt(n));
            if (setOfCustomers.add(calledCustomer)) {
                executor.submit(new MyCallableTask(calledCustomer));
                if (setOfCustomers.size() == customerList.size()) {
                    setOfCustomers.clear();
                }
            } else System.out.println(calledCustomer.getPurchase());
        }
        executor.shutdownNow();
        System.out.println(purchaseBound);
    }
}
