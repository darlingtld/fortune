package fortune.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Entity
@Table(name = "lottery_mark_six")
public class LotteryMarkSix extends Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "one")
    private int one;
    @Column(name = "two")
    private int two;
    @Column(name = "three")
    private int three;
    @Column(name = "four")
    private int four;
    @Column(name = "five")
    private int five;
    @Column(name = "six")
    private int six;
    @Column(name = "timestamp")
    private Date timestamp;

    public LotteryMarkSix() {
    }

    public LotteryMarkSix(int one, int two, int three, int four, int five, int six, Date timestamp) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.timestamp = timestamp;
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
                ", timestamp=" + timestamp +
                '}';
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
