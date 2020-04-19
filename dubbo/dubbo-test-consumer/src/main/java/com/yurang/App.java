package com.yurang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    static  final  Logger logger=LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws InterruptedException {
        System.out.println("开始启动...");
        ClassPathXmlApplicationContext
                classPathXmlApplicationContext=
                new ClassPathXmlApplicationContext(new
                        String[]{"META-INF/spring/" +
                        "application.xml"});
        HelloService
                helloService=(HelloService)classPathXmlApplicationContext.getBean
                ("helloService");
        logger.info("dubbo消费者启动成功...");

        for (int i = 0; i < 1000; i++) {
            TimeUnit.SECONDS.sleep(1);
            logger.info("调用dubbo的服务接口 result:"+helloService.sayHello(String.valueOf(i)));
        }
    }
}

