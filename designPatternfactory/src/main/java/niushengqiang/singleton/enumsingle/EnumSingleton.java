package niushengqiang.singleton.enumsingle;

//单例模式
public enum EnumSingleton {
    INSTANCE;
    private String po;

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }
}
