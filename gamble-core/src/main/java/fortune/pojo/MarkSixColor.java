package fortune.pojo;

/**
 * Created by apple on 15/10/18.
 */
public enum MarkSixColor {
    RED("红"), BLUE("蓝"), GREEN("绿");

    private String color;

    MarkSixColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
