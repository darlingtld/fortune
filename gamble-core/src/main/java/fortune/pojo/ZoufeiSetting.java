package fortune.pojo;

/**
 * Created by lingda on 4/3/16.
 */
public class ZoufeiSetting {
    private String status;
    private Double maxStake;
    private Double curStake;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMaxStake() {
        return maxStake;
    }

    public void setMaxStake(Double maxStake) {
        this.maxStake = maxStake;
    }

    public Double getCurStake() {
        return curStake;
    }

    public void setCurStake(Double curStake) {
        this.curStake = curStake;
    }
}
