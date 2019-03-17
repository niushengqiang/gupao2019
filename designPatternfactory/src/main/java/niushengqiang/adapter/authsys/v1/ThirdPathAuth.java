package niushengqiang.adapter.authsys.v1;

public interface ThirdPathAuth {
    void qqLogin(String username,String password);
    void WechatLogin(String username,String password);
    void phoneLogin(String username,String password);
    void emailLogin(String username,String password);
}
