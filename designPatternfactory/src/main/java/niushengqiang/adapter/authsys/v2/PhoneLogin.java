package niushengqiang.adapter.authsys.v2;

public class PhoneLogin implements LoginInterFace{
    @Override
    public void login(String username, String password) {
        System.out.println("手机登录");
    }

    @Override
    public void register(String username, String password) {
        System.out.println("手机注册");
    }
}
