package samples.activemq.security.plugins;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SenderTCP {

	public static void main(String args[]) throws Exception{
	
	 ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("nio://user1:12595");
	 
     Connection connection = connectionFactory.createConnection("user1","secret");
     connection.start();

     Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
     
     Destination  destination = session.createQueue("QUEUE.00001");
     
     MessageProducer producer = session.createProducer(destination);
     producer.setDeliveryMode(DeliveryMode.PERSISTENT);
     
     for(int i = 0 ; i < 5 ; i++){
    	 TextMessage message = session.createTextMessage("hola");

    	 producer.send(message);
     }
     
     Destination  destinationtopic = session.createTopic("PublicTopicTest");
     
     MessageProducer producerTopic = session.createProducer(destinationtopic);
     producerTopic.setDeliveryMode(DeliveryMode.PERSISTENT);
     
     for(int i = 0 ; i < 5 ; i++){
    	 TextMessage message = session.createTextMessage("hello");

    	 producerTopic.send(message);
     }
     
     
     session.close();
     connection.close();
	}
	
}
