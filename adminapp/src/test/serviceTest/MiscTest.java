package serviceTest;

import java.text.DecimalFormat;

/**
 * Created by tangl9 on 2016-01-04.
 */
public class MiscTest {

    public static void main(String[] args){
        DecimalFormat df = new DecimalFormat("000");
        System.out.println(df.format(1));
        System.out.println(df.format(13));
        System.out.println(df.format(144));
    }
}
