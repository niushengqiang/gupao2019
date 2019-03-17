package niushengqiang.adapter.authsys.v2;

public enum LoginAdapter {
    QQ_ADAPTER("QQ",QQLogin.class),
    WECHAT_ADAPTER("QQ",QQLogin.class),
    PHONE_ADAPTER("QQ",QQLogin.class);


    private String name;
    private Class clazz;

    LoginAdapter(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
