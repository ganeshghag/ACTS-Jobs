package com.icon.products.acts.jobs.utils;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.icon.products.acts.jobs.OCRRequestHandler;

public class JmsMsgSender {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//sendOCRRequestMessage("d:\\temp\\CaseFolders\\Case_101"+i,"form14");
		sendOCRRequestMessage(args[0],args[1]);
	}
	
	public static void sendOCRRequestMessage(String folderPathToCaseDocuments, String formTemplateName){
		try {
			sendMessageToQueue(OCRRequestHandler.OCR_REQUEST_QUEUE, folderPathToCaseDocuments +","+formTemplateName);
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
	}
	
	public static void sendMessageToQueue(String queueName, String inMessage) throws Exception {
		
		// Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
       TextMessage message = session.createTextMessage(inMessage);

        // Tell the producer to send the message
        System.out.println("Sent message["+new Date()+"]: "+ message);
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
	}

}
