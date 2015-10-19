package fortune.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tangl9 on 2015-10-19.
 */
@Entity
@Table(name = "gamble_bet_lottery_mark_six")
public class GambleBetLotteryMarkSix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @NotNull
    @Column(name = "pgroup_id")
    private int pgroupId;
    @NotNull
    @Column(name = "timestamp")
    private Date timestamp;
    @NotNull
    @Column(name = "one")
    private int one;
    @NotNull
    @Column(name = "one_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor oneColor;
    @NotNull
    @Column(name = "two")
    private int two;
    @NotNull
    @Column(name = "two_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor twoColor;
    @NotNull
    @Column(name = "three")
    private int three;
    @NotNull
    @Column(name = "three_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor threeColor;
    @NotNull
    @Column(name = "four")
    private int four;
    @NotNull
    @Column(name = "four_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor fourColor;
    @NotNull
    @Column(name = "five")
    private int five;
    @NotNull
    @Column(name = "five_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor fiveColor;
    @Column(name = "six")
    private int six;
    @NotNull
    @Column(name = "six_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor sixColor;
    @NotNull
    @Column(name = "special")
    private int special;
    @NotNull
    @Column(name = "special_color")
    @Enumerated(EnumType.STRING)
    private MarkSixColor specialColor;
    @NotNull
    @Column(name = "stakes")
    private double stakes;
    @NotNull
    @Column(name = "issue")
    private int issue;

    @Override
    public String toString() {
        return "GambleBetLotteryMarkSix{" +
                "id=" + id +
                ", userId=" + userId +
                ", pgroupId=" + pgroupId +
                ", timestamp=" + timestamp +
                ", one=" + one +
                ", oneColor=" + oneColor +
                ", two=" + two +
                ", twoColor=" + twoColor +
                ", three=" + three +
                ", threeColor=" + threeColor +
                ", four=" + four +
                ", fourColor=" + fourColor +
                ", five=" + five +
                ", fiveColor=" + fiveColor +
                ", six=" + six +
                ", sixColor=" + sixColor +
                ", special=" + special +
                ", specialColor=" + specialColor +
                ", stakes=" + stakes +
                ", issue=" + issue +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPgroupId() {
        return pgroupId;
    }

    public void setPgroupId(int pgroupId) {
        this.pgroupId = pgroupId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public MarkSixColor getOneColor() {
        return oneColor;
    }

    public void setOneColor(MarkSixColor oneColor) {
        this.oneColor = oneColor;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public MarkSixColor getTwoColor() {
        return twoColor;
    }

    public void setTwoColor(MarkSixColor twoColor) {
        this.twoColor = twoColor;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public MarkSixColor getThreeColor() {
        return threeColor;
    }

    public void setThreeColor(MarkSixColor threeColor) {
        this.threeColor = threeColor;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public MarkSixColor getFourColor() {
        return fourColor;
    }

    public void setFourColor(MarkSixColor fourColor) {
        this.fourColor = fourColor;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public MarkSixColor getFiveColor() {
        return fiveColor;
    }

    public void setFiveColor(MarkSixColor fiveColor) {
        this.fiveColor = fiveColor;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public MarkSixColor getSixColor() {
        return sixColor;
    }

    public void setSixColor(MarkSixColor sixColor) {
        this.sixColor = sixColor;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public MarkSixColor getSpecialColor() {
        return specialColor;
    }

    public void setSpecialColor(MarkSixColor specialColor) {
        this.specialColor = specialColor;
    }

    public double getStakes() {
        return stakes;
    }

    public void setStakes(double stakes) {
        this.stakes = stakes;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }
}
