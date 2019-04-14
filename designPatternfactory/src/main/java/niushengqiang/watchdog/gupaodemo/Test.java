package niushengqiang.watchdog.gupaodemo;

import com.google.common.eventbus.EventBus;

public class Test {
    public static void main(String[] args) {
        Question question = new Question();
        question.setName("设计模式怎么学");
        question.setContent("设计模式那么多怎么不全部讲完？");
        BeAtPersion tom = new BeAtPersion("tom");
        BeAtPersion mic = new BeAtPersion("mic");
        BeAtPersion james = new BeAtPersion("james");

        EventBus eventBus = new EventBus();
        eventBus.register(tom);
        eventBus.register(mic);
        eventBus.register(james);
        eventBus.post(question);
    }
}