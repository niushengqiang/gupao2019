package niushengqiang.proxy.rebuild;

import niushengqiang.proxy.Me;
import niushengqiang.proxy.Person;

public class GPTest {
    public static void main(String[] args) throws Exception {
        Person p =(Person)new HeadHunting().getInstance(new Me());
        p.findWork();
        System.out.println(p);
    }
}
