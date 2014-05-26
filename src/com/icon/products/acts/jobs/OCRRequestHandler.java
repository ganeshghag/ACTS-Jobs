package com.icon.products.acts.jobs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import com.icon.products.acts.jobs.utils.FormTemplateParser;

public class OCRRequestHandler {
	
	public final static String OCR_REQUEST_QUEUE = "OCRRequestQ";

	public static void main(String[] args) {
		startEmbeddedBroker();
		listenOCRRequest();
	}
	
	public static void startEmbeddedBroker(){
		BrokerService broker = new BrokerService();
		 
		// configure the broker
		try {
			broker.addConnector("tcp://localhost:61616");
			broker.start();
			System.out.println("Embedded broker started......");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		
	}
	
	public static void listenOCRRequest(){
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			 
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            //connection.setExceptionListener(this);

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(OCR_REQUEST_QUEUE);

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(destination);

            while(true){
	            // Wait for a message
            	System.out.println("waiting for incoming messages....");
	            Message message = consumer.receive();
	
	            if (message instanceof TextMessage) {
	                TextMessage textMessage = (TextMessage) message;
	                String text = textMessage.getText();
	                handleOCRRequest(text);
	            } else {
	                System.out.println("Received NON_TEXT: " + message);
	            }
            }
            
        } catch (Exception e) {
            System.out.println("Exception in listenAndHandleOCRRequest:Caught: " + e);
            e.printStackTrace();
        }finally{
        	try {
				consumer.close();
				session.close();
		        connection.close();
			} catch (JMSException e) {
				System.out.println("Exception while closing JMS connections. Details below:");
				e.printStackTrace();
			}
           
        }

	}
	
	public static void handleOCRRequest(String message){
		System.out.println("Received JMS MSG in OCRRequestHandler::handleOCRRequest(): " + message);
		String[] msgParts = message.split(",");
		try {
			Map retMap = FormTemplateParser.parseFormTemplate(msgParts[1]);
			System.out.println("map returned from parseFormTemplate="+retMap);
		} catch (FileNotFoundException e) {
			System.out.println("exception during FormTemplateParser.parseFormTemplate()"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("exception during FormTemplateParser.parseFormTemplate()"+e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("exception during FormTemplateParser.parseFormTemplate()"+e.getMessage());
			e.printStackTrace();
		}
	}

}
