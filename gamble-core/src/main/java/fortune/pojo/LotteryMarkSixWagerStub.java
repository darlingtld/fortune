package fortune.pojo;

/**
 * Created by tangl9 on 2015-10-21.
 */
public class LotteryMarkSixWagerStub {

    private int number;
    private double stakes;

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "LotteryMarkSixWagerStub{" +
                "number=" + number +
                ", stakes=" + stakes +
                '}';
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getStakes() {
        return stakes;
    }

    public void setStakes(double stakes) {
        this.stakes = stakes;
    }
}
