package fortune.pojo;

/**
 * Created by lingda on 2015/11/9.
 */
public class RealtimeStat {

    private String groupId;
    private int number;
    private int transactions;
    private double stakes;
    private double odds;
    private double balance;
    private String lotteryMarkSixType;
    private String lotteryMarkSixTypeName;
    private String wagerContent;

    @Override
    public String toString() {
        return "RealtimeStat{" +
                "groupId='" + groupId + '\'' +
                ", number=" + number +
                ", transactions=" + transactions +
                ", stakes=" + stakes +
                ", odds=" + odds +
                ", balance=" + balance +
                ", lotteryMarkSixType=" + lotteryMarkSixType +
                '}';
    }

    public String getLotteryMarkSixType() {
        return lotteryMarkSixType;
    }

    public void setLotteryMarkSixType(String lotteryMarkSixType) {
        this.lotteryMarkSixType = lotteryMarkSixType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTransactions() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    public double getStakes() {
        return stakes;
    }

    public void setStakes(double stakes) {
        this.stakes = stakes;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addStakes(double stakes) {
        this.stakes += stakes;
    }

    public void addTransactions(int transactions) {
        this.transactions += transactions;
    }

    public String getWagerContent() {
        return wagerContent;
    }

    public void setWagerContent(String wagerContent) {
        this.wagerContent = wagerContent;
    }

    public String getLotteryMarkSixTypeName() {
        return lotteryMarkSixTypeName;
    }

    public void setLotteryMarkSixTypeName(String lotteryMarkSixTypeName) {
        this.lotteryMarkSixTypeName = lotteryMarkSixTypeName;
    }
}
