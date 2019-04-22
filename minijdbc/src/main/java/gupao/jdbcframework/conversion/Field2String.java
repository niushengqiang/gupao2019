package gupao.jdbcframework.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将E类型的数据转换为String
 */
public class Field2String implements TypeConversion{

    @Override
    public Object change(Class original,Object val) {
        if(val==null) return  val;
        if(original==String.class){return val;}
        if(original==Date.class){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return  sdf.format(val);
        }
        /**
         * 这里类型转换还有很多可以考虑策略模式
         */
        return val.toString();
    }
}
