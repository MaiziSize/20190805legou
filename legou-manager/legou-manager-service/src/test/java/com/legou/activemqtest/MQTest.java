package com.legou.activemqtest;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;




public class MQTest {
	
	/* @Test */
	public void fabuzhe() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");
		ActiveMQConnectionFactory connectionfactory =  (ActiveMQConnectionFactory) applicationContext.getBean("connectionFactory");
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		Destination destination = (Destination) applicationContext.getBean("activeMQQueue");
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				return session.createTextMessage("com-legou-activemqtest");
			}
		});
	
	}
}
