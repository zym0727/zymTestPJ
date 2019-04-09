package zym.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zym
 * *
 */
public class DateUtil {

    static private String common = "yyyy-MM-dd hh:mm:ss";

    static public String dateFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(common);
        return simpleDateFormat.format(date);
    }
}
