package com.quanqinle.myworld.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.UnsupportedEncodingException;

/**
 * 订阅频道，接收（监听）消息
 * @author quanqinle
 */
@Configuration
public class RedisChannelListenerConf {

	@Bean
	MessageListenerAdapter listenerAdapter( ) {
		// 作用：对消息进行序列化
		MessageListenerAdapter adapter = new MessageListenerAdapter(new MyRedisChannelListener());
		adapter.setSerializer(new JdkSerializationRedisSerializer());
		return adapter;
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
	                                        MessageListenerAdapter listenerAdapter) {
		/**
		 * 作用：在Redis cli接收到消息后，通过PatternTopic派发消息到对应的消息监听这，并构造一个线程池任务来调用MessageListener
		 */
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		//订阅所有news.* 频道内容
		container.addMessageListener(listenerAdapter, new PatternTopic("news.*"));
		return container;
	}
}

/**
 * 为了订阅频道，实现监听
 */
class MyRedisChannelListener implements MessageListener {
	Log log = LogFactory.getLog(RedisChannelListenerConf.class);

	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] channel = message.getChannel();
		byte[] bs = message.getBody();
		try {
			String content = new String(bs,"UTF-8");
			String p = new String(channel,"UTF-8");
			log.info("get "+content+" from "+p);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}