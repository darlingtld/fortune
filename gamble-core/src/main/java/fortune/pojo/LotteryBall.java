package fortune.pojo;

/**
 * Created by tangl9 on 2015-10-21.
 */
public enum LotteryBall {
    NUM_1(1, MarkSixColor.RED),
    NUM_2(2, MarkSixColor.RED),
    NUM_3(3, MarkSixColor.BLUE),
    NUM_4(4, MarkSixColor.BLUE),
    NUM_5(5, MarkSixColor.GREEN),
    NUM_6(6, MarkSixColor.GREEN),
    NUM_7(7, MarkSixColor.RED),
    NUM_8(8, MarkSixColor.RED),
    NUM_9(9, MarkSixColor.BLUE),
    NUM_10(10, MarkSixColor.BLUE),
    NUM_11(11, MarkSixColor.GREEN),
    NUM_12(12, MarkSixColor.RED),
    NUM_13(13, MarkSixColor.RED),
    NUM_14(14, MarkSixColor.BLUE),
    NUM_15(15, MarkSixColor.BLUE),
    NUM_16(16, MarkSixColor.GREEN),
    NUM_17(17, MarkSixColor.GREEN),
    NUM_18(18, MarkSixColor.RED),
    NUM_19(19, MarkSixColor.RED),
    NUM_20(20, MarkSixColor.BLUE),
    NUM_21(21, MarkSixColor.GREEN),
    NUM_22(22, MarkSixColor.GREEN),
    NUM_23(23, MarkSixColor.RED),
    NUM_24(24, MarkSixColor.RED),
    NUM_25(25, MarkSixColor.BLUE),
    NUM_26(26, MarkSixColor.BLUE),
    NUM_27(27, MarkSixColor.GREEN),
    NUM_28(28, MarkSixColor.GREEN),
    NUM_29(29, MarkSixColor.RED),
    NUM_30(30, MarkSixColor.RED),
    NUM_31(31, MarkSixColor.BLUE),
    NUM_32(32, MarkSixColor.GREEN),
    NUM_33(33, MarkSixColor.GREEN),
    NUM_34(34, MarkSixColor.RED),
    NUM_35(35, MarkSixColor.RED),
    NUM_36(36, MarkSixColor.BLUE),
    NUM_37(37, MarkSixColor.BLUE),
    NUM_38(38, MarkSixColor.GREEN),
    NUM_39(39, MarkSixColor.GREEN),
    NUM_40(40, MarkSixColor.RED),
    NUM_41(41, MarkSixColor.BLUE),
    NUM_42(42, MarkSixColor.BLUE),
    NUM_43(43, MarkSixColor.GREEN),
    NUM_44(44, MarkSixColor.GREEN),
    NUM_45(45, MarkSixColor.RED),
    NUM_46(46, MarkSixColor.RED),
    NUM_47(47, MarkSixColor.BLUE),
    NUM_48(48, MarkSixColor.BLUE),
    NUM_49(49, MarkSixColor.GREEN);

    private int number;
    private MarkSixColor color;

    LotteryBall(int num, MarkSixColor color) {
        this.number = num;
        this.color = color;
    }

    @Override
    public String toString() {
        return "LotteryBall{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public MarkSixColor getColor() {
        return color;
    }

    public void setColor(MarkSixColor color) {
        this.color = color;
    }
}
