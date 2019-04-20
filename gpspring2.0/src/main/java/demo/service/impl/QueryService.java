package demo.service.impl;

import demo.aspect.LogAspect;
import demo.service.IQueryService;
import gpspring.framework.annotation.GPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询业务
 * @author Tom
 *
 */
@GPService
public class QueryService implements IQueryService {
	Logger log= LoggerFactory.getLogger(QueryService.class);
	/**
	 * 查询
	 */
	public String query(String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
		log.info("这是在业务方法中打印的：" + json);
		return json;
	}

}
