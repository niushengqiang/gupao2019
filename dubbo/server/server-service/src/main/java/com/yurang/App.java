package com.yurang;

import com.alibaba.dubbo.container.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    static  final  Logger logger=LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        Main. main (args);
        logger.info("dubbo 启动完成...");

    }
}
