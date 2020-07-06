import java.util.List;

public class ShoppingAutomation {
  public static void main(String[] args) {
    new AsdaOrderer().createOrder(List.of(
      "haagen dazs",
      "coke zero bottle",
      "margherita"));
  }
}
