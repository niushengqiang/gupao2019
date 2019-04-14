package niushengqiang.adapter.authsys.v1;

public class TheFinalImpl implements AuthSys,ThirdPathAuth{

    private AuthSys original;

    public TheFinalImpl(AuthSys original) {
        this.original = original;
    }

    @Override
    public void login(String username, String password) {
        original.login(username,password);
    }

    @Override
    public void register(String username, String password) {
        original.register(username,password);
    }

    @Override
    public void qqLogin(String username, String password) {
        System.out.println("拓展QQ登录");
    }

    @Override
    public void WechatLogin(String username, String password) {
        System.out.println("拓展Wechat登录");
    }

    @Override
    public void phoneLogin(String username, String password) {
        System.out.println("拓展手机登录");
    }

    @Override
    public void emailLogin(String username, String password) {
        System.out.println("拓展邮件登录");
    }
}
