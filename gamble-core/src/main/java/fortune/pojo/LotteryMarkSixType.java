package fortune.pojo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-20.
 */
public enum LotteryMarkSixType {
    SPECIAL("特码"),

    ZODIAC("生肖"),       // 生肖类型总称
    ZODIAC_SHU("鼠"),
    ZODIAC_NIU("牛"),
    ZODIAC_HU("虎"),
    ZODIAC_TU("兔"),
    ZODIAC_LONG("龙"),
    ZODIAC_SHE("蛇"),
    ZODIAC_MA("马"),
    ZODIAC_YANG("羊"),
    ZODIAC_HOU("猴"),
    ZODIAC_JI("鸡"),
    ZODIAC_GOU("狗"),
    ZODIAC_ZHU("猪"),

    COLOR("色波"),        // 色波类型总称
    RED("红波"),
    BLUE("蓝波"),
    GREEN("绿波"),

    HALF_WAVE("半波"),       // 半波类型总称
    WAVE_RED_SHUANG("红双"),
    WAVE_RED_DAN("红单"),
    WAVE_RED_DA("红大"),
    WAVE_RED_XIAO("红小"),

    WAVE_BLUE_SHUANG("蓝双"),
    WAVE_BLUE_DAN("蓝单"),
    WAVE_BLUE_DA("蓝大"),
    WAVE_BLUE_XIAO("蓝小"),

    WAVE_GREEN_SHUANG("绿双"),
    WAVE_GREEN_DAN("绿单"),
    WAVE_GREEN_DA("绿大"),
    WAVE_GREEN_XIAO("绿小"),

    SUM_ZODIAC("合肖"), // ballNumber表示是几肖，总类型
    
    ZHENG_BALL("正码"),
    
    ZHENG_SPECIFIC("正码特"),      // 正码特类型总称
    ZHENG_SPECIFIC_1("正码特1"), 
    ZHENG_SPECIFIC_2("正码特2"), 
    ZHENG_SPECIFIC_3("正码特3"), 
    ZHENG_SPECIFIC_4("正码特4"), 
    ZHENG_SPECIFIC_5("正码特5"), 
    ZHENG_SPECIFIC_6("正码特6"), 

    ZHENG_1_6("正码1到6"),
    
    // 正码1-6，两面会用到
    DAN("单"),
    SHUANG("双"),
    DA("大"),
    XIAO("小"),
    HEDAN("合单"),
    HESHUANG("合双"),
    HEDA("合大"),
    HEXIAO("合小"),
    WEIDA("尾大"),
    WEIXIAO("尾小"),
    HEWEIDA("合尾大"),
    HEWEIXIAO("合尾小"),
    JIAQIN("家禽"),
    YESHOU("野兽"),
    
    JOINT_3_ALL("三全中"),
    JOINT_3_2("三中二"),
    JOINT_2_ALL("二全中"),
    JOINT_2_SPECIAL("二中特"),
    JOINT_SPECIAL("特串"),
    
    NOT_5("五不中"),
    NOT_6("六不中"),
    NOT_7("七不中"),
    NOT_8("八不中"),
    NOT_9("九不中"),
    NOT_10("十不中"),
    NOT_11("十一不中"),
    NOT_12("十二不中"),
    
    PASS_DAN("过关单"), // 过关的子类型
    PASS_SHUANG("过关双"),
    PASS_DA("过关大"),
    PASS_XIAO("过关小"),
    PASS_RED("过关红"),
    PASS_GREEN("过关绿"),
    PASS_BLUE("过关蓝"),
    PASS("过关"), // 总类型
    
    ONE_ZODIAC("一肖"),
    TAIL_NUM("尾数"), // ballNumber表示是几尾
    
    JOINT_ZODIAC("连肖"),  // 连肖类型总称
    JOINT_ZODIAC_PING("连肖（平肖）"), //没有包含本年生肖
    JOINT_ZODIAC_ZHENG("连肖（正肖）"), //包含本年生肖

    // 用于连肖统计
    JOINT_ZODIAC_2("二肖"), 
    JOINT_ZODIAC_3("三肖"), 
    JOINT_ZODIAC_4("四肖"), 
    JOINT_ZODIAC_5("五肖"), 
    
    TWO_FACES("两面"),
    
    // 连尾
    JOINT_TAIL("连尾"),   // 连尾类型总称
    
    JOINT_TAIL_2("二尾"),
    JOINT_TAIL_3("三尾"),
    JOINT_TAIL_4("四尾"),
    
    JOINT_TAIL_NOT_2("二尾不中"),
    JOINT_TAIL_NOT_3("三尾不中"),
    JOINT_TAIL_NOT_4("四尾不中"),
        
    
    ////////////////////////DELETED BELOW//////////////////
    /* 下面的这些类别还没用到 */
    @Deprecated
    NUMBER("数字"),
    @Deprecated
    ZHONG("中"),
    @Deprecated
    SPECIAL_WEIDA("特尾大"),
    @Deprecated
    SPECIAL_WEIXIAO("特尾小"),
    @Deprecated
    SPECIAL_HEWEIDA("合尾大"),
    @Deprecated
    SPECIAL_HEWEIXIAO("合尾小"),
    

    ONE_DAN("正码一单"),
    ONE_SHUANG("正码一双"),
    ONE_DA("正码一大"),
    ONE_XIAO("正码一小"),
    ONE_HONGBO("正码一红波"),
    ONE_LVBO("正码一绿波"),
    ONE_HEDAN("正码一合单"),
    ONE_HESHUANG("正码一合双"),

    TWO_DAN("正码二单"),
    TWO_SHUANG("正码二双"),
    TWO_DA("正码二大"),
    TWO_XIAO("正码二小"),
    TWO_HONGBO("正码二红波"),
    TWO_LVBO("正码二绿波"),
    TWO_HEDAN("正码二合单"),
    TWO_HESHUANG("正码二合双"),

    THREE_DAN("正码三单"),
    THREE_SHUANG("正码三双"),
    THREE_DA("正码三大"),
    THREE_XIAO("正码三小"),
    THREE_HONGBO("正码三红波"),
    THREE_LVBO("正码三绿波"),
    THREE_HEDAN("正码三合单"),
    THREE_HESHUANG("正码三合双"),

    FOUR_DAN("正码四单"),
    FOUR_SHUANG("正码四双"),
    FOUR_DA("正码四大"),
    FOUR_XIAO("正码四小"),
    FOUR_HONGBO("正码四红波"),
    FOUR_LVBO("正码四绿波"),
    FOUR_HEDAN("正码四合单"),
    FOUR_HESHUANG("正码四合双"),

    FIVE_DAN("正码五单"),
    FIVE_SHUANG("正码五双"),
    FIVE_DA("正码五大"),
    FIVE_XIAO("正码五小"),
    FIVE_HONGBO("正码五红波"),
    FIVE_LVBO("正码五绿波"),
    FIVE_HEDAN("正码五合单"),
    FIVE_HESHUANG("正码五合双"),

    SIX_DAN("正码六单"),
    SIX_SHUANG("正码六双"),
    SIX_DA("正码六大"),
    SIX_XIAO("正码六小"),
    SIX_HONGBO("正码六红波"),
    SIX_LVBO("正码六绿波"),
    SIX_HEDAN("正码六合单"),
    SIX_HESHUANG("正码六合双"),

    @Deprecated
    SPECIAL_DAN("正码特单"),
    @Deprecated
    SPECIAL_SHUANG("正码特双"),
    @Deprecated
    SPECIAL_DA("正码特大"),
    @Deprecated
    SPECIAL_XIAO("正码特小"),
    @Deprecated
    SPECIAL_HONGBO("正码特红波"),
    @Deprecated
    SPECIAL_LVBO("正码特绿波"),
    @Deprecated
    SPECIAL_HEDAN("正码特合单"),
    @Deprecated
    SPECIAL_HESHUANG("正码特合双"),
    @Deprecated
    SPECIAL_HEDA("正码特合大"),
    @Deprecated
    SPECIAL_HEXIAO("正码特合小");


    private String type;

    LotteryMarkSixType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Deprecated
    public static List<LotteryMarkSixType> getRealTimeSpecialTypeList() {
        return Arrays.asList(
                SPECIAL_DAN,
                SPECIAL_SHUANG,
                SPECIAL_DA,
                SPECIAL_XIAO,
                SPECIAL_HEDAN,
                SPECIAL_HESHUANG,
                SPECIAL_HEDA,
                SPECIAL_HEXIAO,
                SPECIAL_HEWEIXIAO,
                SPECIAL_HEWEIDA);
    }

    public static List<LotteryMarkSixType> getRealTimeAnimalList() {
        return Arrays.asList(
                ZODIAC_SHU,
                ZODIAC_NIU,
                ZODIAC_HU,
                ZODIAC_TU,
                ZODIAC_LONG,
                ZODIAC_SHE,
                ZODIAC_MA,
                ZODIAC_YANG,
                ZODIAC_HOU,
                ZODIAC_JI,
                ZODIAC_GOU,
                ZODIAC_ZHU);
    }

    public static List<LotteryMarkSixType> getRealTimeAnimalTypeList() {
        return Arrays.asList(
                JIAQIN,
                YESHOU);
    }

    public static List<LotteryMarkSixType> getRealTimeWaveTypeList() {
        return Arrays.asList(
                WAVE_RED_SHUANG,
                WAVE_RED_DAN,
                WAVE_RED_DA,
                WAVE_RED_XIAO,
                WAVE_BLUE_SHUANG,
                WAVE_BLUE_DAN,
                WAVE_BLUE_DA,
                WAVE_BLUE_XIAO,
                WAVE_GREEN_SHUANG,
                WAVE_GREEN_DAN,
                WAVE_GREEN_DA,
                WAVE_GREEN_XIAO);
    }

    @Deprecated
    public static List<LotteryMarkSixType> getRealTimeSpecialTailTypeList() {
        return Arrays.asList(
                SPECIAL_WEIDA,
                SPECIAL_WEIXIAO);
    }

    public static List<LotteryMarkSixType> getRealTimeColorTypeList() {
        return Arrays.asList(
                RED,
                BLUE,
                GREEN);
    }

    public static List<LotteryMarkSixType> getRealTimeBigOrSmallTypeList() {
        return Arrays.asList(
                DA,
                ZHONG,
                XIAO);
    }
    
    public static List<LotteryMarkSixType> getRealTimeZheng16TypeList() {
        return Arrays.asList(
                DAN,
                SHUANG,
                DA,
                XIAO,
                RED,
                BLUE,
                GREEN,
                HEDAN,
                HESHUANG);
    }
    
    public static List<LotteryMarkSixType> getRealTimeZhengSpecificList() {
        return Arrays.asList(
                ZHENG_SPECIFIC_1,
                ZHENG_SPECIFIC_2,
                ZHENG_SPECIFIC_3,
                ZHENG_SPECIFIC_4,
                ZHENG_SPECIFIC_5,
                ZHENG_SPECIFIC_6
                );
    }
    
    public static List<LotteryMarkSixType> getRealTimeNotList() {
        return Arrays.asList(
                NOT_5,
                NOT_6,
                NOT_7,
                NOT_8,
                NOT_9,
                NOT_10,
                NOT_11,
                NOT_12);
    }
    
    public static List<LotteryMarkSixType> getRealTimeJointTailList() {
        return Arrays.asList(
                JOINT_TAIL_2,
                JOINT_TAIL_3,
                JOINT_TAIL_4,
                JOINT_TAIL_NOT_2,
                JOINT_TAIL_NOT_3,
                JOINT_TAIL_NOT_4
                );
    }
    
    public static List<LotteryMarkSixType> getRealTimeJointZodiacList() {
        return Arrays.asList(
                JOINT_ZODIAC_2,
                JOINT_ZODIAC_3,
                JOINT_ZODIAC_4,
                JOINT_ZODIAC_5
                );
    }
    
    public static List<LotteryMarkSixType> getRealTimeTwoFacesList() {
        return Arrays.asList(
                DAN,
                SHUANG,
                DA,
                XIAO,
                HEDAN,
                HESHUANG,
                HEDA,
                HEXIAO,
                WEIDA,
                WEIXIAO,
                HEWEIDA,
                HEWEIXIAO,
                JIAQIN,
                YESHOU
                );
    }
    
    public static List<LotteryMarkSixType> getTypeList4AllStats() {
        return Arrays.asList(
                SPECIAL,
                TWO_FACES,
                HALF_WAVE,
                COLOR,
                ZODIAC,
                SUM_ZODIAC,
                ZHENG_BALL,
                ZHENG_1_6,
                ZHENG_SPECIFIC,
                JOINT_3_ALL,
                JOINT_3_2,
                JOINT_2_ALL,
                JOINT_2_SPECIAL,
                JOINT_SPECIAL,
                NOT_5,
                NOT_6,
                NOT_7,
                NOT_8,
                NOT_9,
                NOT_10,
                NOT_11,
                NOT_12,
                JOINT_ZODIAC,
                JOINT_TAIL,
                ONE_ZODIAC,
                TAIL_NUM
                );
    }
}
