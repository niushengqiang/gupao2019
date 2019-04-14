package niushengqiang.watchdog.gupaodemo;

import com.google.common.eventbus.Subscribe;

public class BeAtPersion implements Persion {
    private String name;

    public BeAtPersion(String name) {
        this.name = name;
    }

    @Override
    @Subscribe
    public void receiveMessage(Question question) {
       System.out.println(this.name+"接受到了问题---》");
       System.out.println("问题名称："+question.getName());
       System.out.println("问题详情："+question.getContent());
    }
}
