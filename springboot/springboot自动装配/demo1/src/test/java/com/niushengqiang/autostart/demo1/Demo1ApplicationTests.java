package com.niushengqiang.autostart.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

	@Test
	public void contextLoads() {
	}

	static  ThreadLocal threadLocal=new ThreadLocal();
	@Test
	public void testTL(){
		threadLocal.set("123");
		threadLocal.get();
	}


}
