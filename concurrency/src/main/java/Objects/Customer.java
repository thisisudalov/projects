package Objects;

public class Customer {
    private int goodsAmount;
    private int purchase;
    public Customer() {
        goodsAmount = 0;
        purchase = 0;
    }

    public void increasePurchases() {
        purchase++;
    }

    public void increaseGoods(int amount) {
        goodsAmount += amount;
    }

    public int getPurchase() {
        return purchase;
    }

    public int getGoodsAmount() {
        return goodsAmount;
    }

    @Override
    public String toString() {
        return "Purchases: " + purchase + " Products amount: " + goodsAmount;
    }
}
