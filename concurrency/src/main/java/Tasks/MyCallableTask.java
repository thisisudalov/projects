package Tasks;
import Main.Main;
import Objects.Customer;
import Objects.Market;
import java.util.Random;
import java.util.concurrent.Callable;

public class MyCallableTask implements Callable<Boolean> {
    Customer customer;
    public MyCallableTask(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Boolean call() {
        Random random = new Random();

        int a = random.nextInt(10) + 1;
        customer.increaseGoods(Market.takeItems(a));
        customer.increasePurchases();
        return true;
    }
}
