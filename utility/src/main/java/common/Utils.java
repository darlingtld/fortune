package common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
public class Utils {

    public static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static final String HEADER_MESSAGE = "message";
    private static final DecimalFormat df = new DecimalFormat("000");

    public static String yyyyMMddHHmmss2Format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String yyyyMMdd2Format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatNumber000(Object number) {
        return df.format(number);
    }
    
    public static String listToSqlStr(List<String> strList, String splitStr) {
        return "'" + listToStr(strList, "','") + "'";
    }
    
    public static String listToStr(List<String> strList, String splitStr) {
        StringBuffer sb = new StringBuffer();
        for (String str : strList) {
            sb.append(str).append(splitStr);
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - splitStr.length()) : "";
    }

}
