package niushengqiang.adapter.authsys.v2;

public class WeChaLogin implements LoginInterFace{
    @Override
    public void login(String username, String password) {
        System.out.println("微信登录");
    }

    @Override
    public void register(String username, String password) {
        System.out.println("微信注册");
    }
}
