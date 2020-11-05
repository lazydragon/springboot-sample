package andrew.ren.springbootsample;

import java.time.Duration;
import org.apache.commons.lang.RandomStringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@RestController
public class SampleController {

	@Value("${springbootsample.redis.host}")
    private String redis_host;
    
    @Value("${springbootsample.redis.port}")
    private int redis_port;
    
    @Value("${springbootsample.redis.connection}")
    private int redis_connection;
    
    private final JedisPool pool;
    
    Logger logger = LoggerFactory.getLogger(SampleController.class);
    
    public SampleController() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxTotal(redis_connection);
	    poolConfig.setMinIdle(redis_connection/2);
	    poolConfig.setTestOnBorrow(true);
	    poolConfig.setTestOnReturn(true);
	    poolConfig.setTestWhileIdle(true);
	    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
	    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
	    poolConfig.setNumTestsPerEvictionRun(3);
	    poolConfig.setBlockWhenExhausted(true);
	    
        this.pool = new JedisPool(poolConfig, redis_host, redis_port);
        logger.error("An ERROR Message");
    }
    
	@RequestMapping("/set")
	public String set(@RequestParam String id) {
		Jedis jedis = null;
		String result;
		
		jedis = new Jedis(redis_host, redis_port);
		result = jedis.set(id, RandomStringUtils.random(15));
		logger.error(jedis.get(id));
		
        try{
        	logger.error("An ERROR Message");
            jedis = pool.getResource();
            logger.error("2222");
            result = jedis.set(id, RandomStringUtils.random(15));
        }finally{
            if(null != jedis)
                jedis.close();
        }
        
        return result;
	}

}