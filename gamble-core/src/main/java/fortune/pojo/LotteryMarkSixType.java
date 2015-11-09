package fortune.pojo;

/**
 * Created by tangl9 on 2015-10-20.
 */
public enum LotteryMarkSixType {
    SPECIAL("特码"),
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
    RED("红波"),
    BLUE("蓝波"),
    GREEN("绿波"),
    
    
    
    
    
    
    
    
    
    NUMBER("数字"),

    DAN("单"),
    SHUANG("双"),
    DA("大"),
    XIAO("小"),
    HEDAN("合单"),
    HESHUANG("合双"),
    HEDA("合大"),
    HEXIAO("合小"),
    SPECIAL_WEIDA("特尾大"),
    SPCIAL_WEIXIAO("特尾小"),
    HEWEIDA("合尾大"),
    HEWEIXIAO("合尾小"),
    JIAQIN("家禽"),
    YESHOU("野兽"),
    WEIDA("尾大"),
    WEIXIAO("尾小"),

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

    SPECIAL_DAN("正码特单"),
    SPECIAL_SHUANG("正码特双"),
    SPECIAL_DA("正码特大"),
    SPECIAL_XIAO("正码特小"),
    SPECIAL_HONGBO("正码特红波"),
    SPECIAL_LVBO("正码特绿波"),
    SPECIAL_HEDAN("正码特合单"),
    SPECIAL_HESHUANG("正码特合双"),
    SPECIAL_HEDA("正码特合大"),
    SPECIAL_HEXIAO("正码特合小"),

    RED_SHUANG("红双"),
    RED_DAN("红单"),
    RED_DA("红大"),
    RED_XIAO("红小"),

    BLUE_SHUANG("蓝双"),
    BLUE_DAN("蓝单"),
    BLUE_DA("蓝大"),
    BLUE_XIAO("蓝小"),

    GREEN_SHUANG("绿双"),
    GREEN_DAN("绿单"),
    GREEN_DA("绿大"),
    GREEN_XIAO("绿小");


    private String type;

    LotteryMarkSixType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }


}
