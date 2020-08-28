import java.util.ArrayList;
import java.util.List;

public class Customer {
    String name;
    private List<Goods> listOfGoods;
    private int purchase;
    public Customer(String name) {
        listOfGoods = new ArrayList<>(200);
        purchase = 0;
        this.name = name;
    }

    public void increasePurchases() {
        purchase++;
    }

    public List<Goods> getListOfGoods() {
        return listOfGoods;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Purchases: " + purchase + " Products amount: " + listOfGoods.size();
    }
}
