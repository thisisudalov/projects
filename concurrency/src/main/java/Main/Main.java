package Main;

import Logic.PurchaseController;
import Objects.Customer;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Customer> customerList = new ArrayList<>();
        PurchaseController p = new PurchaseController();

        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
            if (n <= 0) throw new IllegalArgumentException("Argument must me more than 0");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No arguments given");
            return;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }


        for (int i = 0; i < n; i++) {
            customerList.add(new Customer());
        }

        p.makePurchases(customerList, n);
        int result = 0;
        for (Customer customer : customerList) {
            result+=customer.getGoodsAmount();
        }
        System.out.println("Customer list out, amount of goods = " + result);
        customerList.forEach(System.out::println);
    }
}
