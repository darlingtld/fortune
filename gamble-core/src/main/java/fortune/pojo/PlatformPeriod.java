package fortune.pojo;

/**
 * Created by tangl9 on 2016-01-06.
 */
public class PlatformPeriod {
    private boolean settingOddsPeriod = false;
    private boolean wagePeriod = false;

    public boolean isSettingOddsPeriod() {
        return settingOddsPeriod;
    }

    public void setSettingOddsPeriod(boolean settingOddsPeriod) {
        this.settingOddsPeriod = settingOddsPeriod;
    }

    public boolean isWagePeriod() {
        return wagePeriod;
    }

    public void setWagePeriod(boolean wagePeriod) {
        this.wagePeriod = wagePeriod;
    }
}
