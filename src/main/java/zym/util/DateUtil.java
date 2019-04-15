package zym.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zym
 * *
 */
public class DateUtil {

    static private String common = "yyyy-MM-dd HH:mm:ss";

    static private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(common);

    static public String dateFormat(Date date){
        return simpleDateFormat.format(date);
    }

    static public Date getNow() throws ParseException {
        return simpleDateFormat.parse(simpleDateFormat.format(new Date()));
    }

    static public String getNowTimeString() {
        return simpleDateFormat.format(new Date());
    }
}
