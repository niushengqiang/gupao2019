package niushengqiang.proxy.selfproxy;

import niushengqiang.proxy.Me;
import niushengqiang.proxy.Person;

public class SelfProxyTest {

    public static void main(String[] args){
        for (int i = 0; i < 100; i++) {
            Person instance = GenerateHeadhunting.getInstance(Me.class);
            System.out.println(instance);
            instance.findWork();
        }
    }
}
