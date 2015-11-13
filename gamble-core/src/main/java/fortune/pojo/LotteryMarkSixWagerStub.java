package fortune.pojo;

/**
 * Created by tangl9 on 2015-10-21.
 */
public class LotteryMarkSixWagerStub {

	private int number;
	private double stakes;
	private LotteryMarkSixType lotteryMarkSixType;

	public LotteryMarkSixWagerStub() {
	}

	public LotteryMarkSixWagerStub(int number, double stakes, LotteryMarkSixType lotteryMarkSixType) {
		this.number = number;
		this.stakes = stakes;
		this.lotteryMarkSixType = lotteryMarkSixType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "LotteryMarkSixWagerStub{" + "number=" + number + ", stakes=" + stakes + '}';
	}

	public double getStakes() {
		return stakes;
	}

	public void setStakes(double stakes) {
		this.stakes = stakes;
	}

	public LotteryMarkSixType getLotteryMarkSixType() {
		return lotteryMarkSixType;
	}

	public void setLotteryMarkSixType(LotteryMarkSixType lotteryMarkSixType) {
		this.lotteryMarkSixType = lotteryMarkSixType;
	}
}
