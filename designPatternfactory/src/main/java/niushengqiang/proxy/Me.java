package niushengqiang.proxy;

public class Me implements Person{

    @Override
    public int findWork() {
        System.out.println("要求:工资高加班少...");
        return 0;
    }
}
