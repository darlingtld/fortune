package fortune.pojo;

/**
 * Created by tangl9 on 2015-10-21.
 */
public class LotteryMarkSixWagerStub {

    private int number;
    private double stakes;

    public LotteryMarkSixWagerStub() {
    }

    public LotteryMarkSixWagerStub(int number, double stakes) {
        this.number = number;
        this.stakes = stakes;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "LotteryMarkSixWagerStub{" +
                "number=" + number +
                ", stakes=" + stakes +
                '}';
    }

    public double getStakes() {
        return stakes;
    }

    public void setStakes(double stakes) {
        this.stakes = stakes;
    }
}
