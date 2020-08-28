import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static List<Customer> customerList = new ArrayList<>(5);
    static List<Goods> allGoods = new ArrayList<>();

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Callable<Boolean>> tasks = new ArrayList<>();

        for (int i = 0; i<1000; i++) {
            allGoods.add(new Goods());
        }
        for (int i = 0; i < 5; i++) {
            tasks.add(new MyCallableTask());
        }
        while (allGoods.size() != 0) {
            try {
                executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Customer list out");
        customerList.forEach(System.out::println);
        executor.shutdown();
    }
}
