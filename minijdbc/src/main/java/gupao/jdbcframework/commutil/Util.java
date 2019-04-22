package gupao.jdbcframework.commutil;

public class Util {
    public static   String getLowverCase(String str){
        char[] chars = str.toCharArray();
        chars[0]= (char) (chars[0]+32);
        return new String(chars);
    }
}
