package niushengqiang.adapter.authsys.v2;

public class QQLogin implements LoginInterFace{
    @Override
    public void login(String username, String password) {
        System.out.println("QQ登录");
    }

    @Override
    public void register(String username, String password) {
        System.out.println("QQ注册");
    }
}
