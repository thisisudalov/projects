import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class MyCallableTask implements Callable<Boolean> {
    private Customer customer;
    static String name = "1";
    MyCallableTask() {
        customer = new Customer(name);
        name+=1;
        Main.customerList.add(customer);
    }

    private static synchronized void callLogics(Customer customer, int a) {
        try {
            List<Goods> sublist = Main.allGoods.subList(0, a);
            customer.getListOfGoods().addAll(sublist);
            Main.allGoods.removeAll(sublist);
            customer.increasePurchases();
        } catch (IndexOutOfBoundsException e) {
            if (Main.allGoods.size() > 0) {
            List<Goods> sublist = Main.allGoods.subList(0, Main.allGoods.size()-1);
            customer.getListOfGoods().addAll(sublist);
            Main.allGoods.removeAll(sublist);
            customer.increasePurchases();
            } else return;
        }
    }

    @Override
    public Boolean call() {
        Random random = new Random();
        int a = random.nextInt(10) + 1;
            callLogics(customer, a);
            //System.out.println(customer);
            //System.out.println("a = " + a);
            return true;
    }
}
