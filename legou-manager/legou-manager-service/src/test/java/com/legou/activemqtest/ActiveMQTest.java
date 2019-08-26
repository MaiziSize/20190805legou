package com.legou.activemqtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQTest {
	//生产者
	@Test
	public void wushuang() throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//通过连接工厂可以获得连接
		Connection connection = connectionFactory.createConnection();
		//启动连接
		connection.start();
		//通过connection来创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//使用session来创建query
		Queue queue = session.createQueue("wushuangsanguo");
		// 在创建producer 需要传递producer发送消息的目的地
		MessageProducer message =  session.createProducer(queue);
		//创建消息
		TextMessage text = session.createTextMessage("我要玩风花雪月");
		//发送消息
		message.send(text);
		
		//关闭资源
		message.close();
		session.close();
		connection.close();
	}
	
	
	@Test
	public void jieshou() throws Exception{
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//通过连接工厂可以获得连接
		Connection connection = connectionFactory.createConnection();
		//开启连接
		connection.start();
		// 创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建queue createQueue需要传递queue的名字如果该名字存在则不创建
		Queue queue = session.createQueue("wushuangsanguo");
		// 创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//创建匿名内部类接收消息
		consumer.setMessageListener(new MessageListener() {			
			@Override
			public void onMessage(Message message) {
				//将接收的消息转化成Text类型
				TextMessage text = (TextMessage) message;	
				try {
					//
					String string = text.getText();
					System.out.println(string);
					if(!string.equals("")) {
						System.out.println("风花雪月真好玩");	
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});		
		//阻塞自动接收消息
		System.in.read();
		//关闭消费者连接
		consumer.close();
		//关闭session
		session.close();
		//关闭MQ的连接
		connection.close();		
	}
	
	
	public void jieshouzhe1() throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("heihun3");
		MessageProducer message = session.createProducer(queue);
		TextMessage Text = session.createTextMessage("我是傻逼");
		message.send(Text);
		//关闭资源
		message.close();
		session.close();
		connection.close();
	}
}
