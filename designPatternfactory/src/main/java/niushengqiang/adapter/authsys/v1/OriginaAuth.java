package niushengqiang.adapter.authsys.v1;


/**
 *
 */
public class OriginaAuth implements AuthSys{

    @Override
    public void login(String username, String password) {
        System.out.println("登录用户名"+username);
        System.out.println("登录用户密码"+password);
    }

    @Override
    public void register(String username,String password) {
        System.out.println("注册用户名"+username);
        System.out.println("注册用户密码"+password);

    }
}
