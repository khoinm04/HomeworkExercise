
package sum25.khoinm.homework2.pojo;

public class CartItem {
    private Orchid orchid;
    private int quantity;

    public CartItem(Orchid orchid, int quantity) {
        this.orchid = orchid;
        this.quantity = quantity;
    }

    public Orchid getOrchid() { return orchid; }
    public void setOrchid(Orchid orchid) { this.orchid = orchid; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}