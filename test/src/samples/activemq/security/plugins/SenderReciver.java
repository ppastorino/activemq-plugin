package samples.activemq.security.plugins;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;


public class SenderReciver {

	public static void main(String[] args) throws Exception {
		durableConsume("user1", "secret");

	}
	
	
	public static void durableConsume(String userName, String password){
		try {

			Context context = new InitialContext();

			ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
						
			Connection connection = factory.createConnection(userName, password);
			connection.setClientID("user1");
			connection.setExceptionListener(new MyExceptionListener());
			connection.start();
			
			
			Session session = connection.createSession(true,Session.SESSION_TRANSACTED);
			
		
			Queue queue = (Queue) context.lookup("myQueueLookupuser1");
			MessageConsumer messageConsumer = session.createConsumer(queue);
			
			for (int i = 0 ; i <5 ; i++){
				TextMessage receivedMessage = (TextMessage) messageConsumer.receive();
				
				if (receivedMessage != null) {
					System.out.println("Topic "+userName + "->"
							+ receivedMessage.getText());
				} else {
					System.out.println(userName + "->"
							+ "No message received within the given timeout!");
				}
			}
		
			connection.close();
			context.close();
		
		} catch (Exception exp) {
			System.out.println("Caught exception, exiting.");
			exp.printStackTrace(System.out);
			System.exit(1);
		}


	}
	
	
	private static class MyExceptionListener implements ExceptionListener {

		public void onException(JMSException exception) {
			System.out.println("Connection ExceptionListener fired, exiting.");
			exception.printStackTrace(System.out);
			System.exit(1);
		}
	}
}