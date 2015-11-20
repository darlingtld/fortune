package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
public class Utils {

    public static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static final String HEADER_MESSAGE = "message";

    public static String yyyyMMddHHmmss2Format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
