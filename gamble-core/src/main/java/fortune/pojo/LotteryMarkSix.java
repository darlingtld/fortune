package fortune.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Entity
@Table(name = "lottery_mark_six")
public class LotteryMarkSix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "one")
    private int one;
    @NotNull
    @Column(name = "two")
    private int two;
    @NotNull
    @Column(name = "three")
    private int three;
    @NotNull
    @Column(name = "four")
    private int four;
    @NotNull
    @Column(name = "five")
    private int five;
    @NotNull
    @Column(name = "six")
    private int six;
    @NotNull
    @Column(name = "special")
    private int special;
    @Column(name = "issue")
    private int issue;
    @Column(name = "timestamp")
    private Date timestamp = new Date();

    public LotteryMarkSix() {
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "LotteryMarkSix{" +
                "id=" + id +
                ", one=" + one +
                ", two=" + two +
                ", three=" + three +
                ", four=" + four +
                ", five=" + five +
                ", six=" + six +
                ", special=" + special +
                ", issue=" + issue +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
