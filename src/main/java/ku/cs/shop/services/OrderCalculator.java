package ku.cs.shop.services;

public class OrderCalculator {
    private double x;
    private int y;

    public OrderCalculator(double x, int y) {
        this.x = x;
        this.y = y;
    }

    public double MultiplyProduct(){
        return this.x * this.y;
    }
}
