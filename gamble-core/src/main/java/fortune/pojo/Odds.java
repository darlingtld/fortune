package fortune.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Entity
@Table(name = "odds")
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_id")
    private LotteryMarkSix lotteryMarkSix;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pgroup_id")
    private PGroup pGroup;
    @NotNull
    @Column(name = "value")
    private double value;
    @NotNull
    @Column(name = "update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return "Odds{" +
                "id=" + id +
                ", lotteryMarkSix=" + lotteryMarkSix +
                ", pGroup=" + pGroup +
                ", value=" + value +
                ", updateTime=" + updateTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LotteryMarkSix getLotteryMarkSix() {
        return lotteryMarkSix;
    }

    public void setLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        this.lotteryMarkSix = lotteryMarkSix;
    }

    public PGroup getpGroup() {
        return pGroup;
    }

    public void setpGroup(PGroup pGroup) {
        this.pGroup = pGroup;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
