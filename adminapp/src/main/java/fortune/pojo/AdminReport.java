package fortune.pojo;

import javax.persistence.Transient;

public class AdminReport {

    private int id;
    private String pgroupId;
    private int totalTransactions;
    private double totalStakes;
    private double userResult;
    private double pgroupResult;
    private double zoufeiStakes;
    private double zoufeiResult;
    private double pgroupTotalResult;
    private String remark;
    private LotteryMarkSixType wagerType;
    private String wagerTypeName;
    
    @Transient
    private PGroup pgroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPgroupId() {
        return pgroupId;
    }

    public void setPgroupId(String pgroupId) {
        this.pgroupId = pgroupId;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getTotalStakes() {
        return totalStakes;
    }

    public void setTotalStakes(double totalStakes) {
        this.totalStakes = totalStakes;
    }

    public double getUserResult() {
        return userResult;
    }

    public void setUserResult(double userResult) {
        this.userResult = userResult;
    }

    public double getPgroupResult() {
        return pgroupResult;
    }

    public void setPgroupResult(double pgroupResult) {
        this.pgroupResult = pgroupResult;
    }

    public double getZoufeiStakes() {
        return zoufeiStakes;
    }

    public void setZoufeiStakes(double zoufeiStakes) {
        this.zoufeiStakes = zoufeiStakes;
    }

    public double getZoufeiResult() {
        return zoufeiResult;
    }

    public void setZoufeiResult(double zoufeiResult) {
        this.zoufeiResult = zoufeiResult;
    }

    public double getPgroupTotalResult() {
        return pgroupTotalResult;
    }

    public void setPgroupTotalResult(double pgroupTotalResult) {
        this.pgroupTotalResult = pgroupTotalResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PGroup getPgroup() {
        return pgroup;
    }

    public void setPgroup(PGroup pgroup) {
        this.pgroup = pgroup;
    }
    
    public LotteryMarkSixType getWagerType() {
        return wagerType;
    }

    public void setWagerType(LotteryMarkSixType wagerType) {
        this.wagerType = wagerType;
    }

    public String getWagerTypeName() {
        return wagerTypeName;
    }

    public void setWagerTypeName(String wagerTypeName) {
        this.wagerTypeName = wagerTypeName;
    }
    
}
