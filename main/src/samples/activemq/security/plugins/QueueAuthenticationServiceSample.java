package samples.activemq.security.plugins;

import java.util.HashMap;

public class QueueAuthenticationServiceSample 
implements QueueAuthenticationService {

	public QueueAuthenticationServiceSample() {
		super();
		this.userCredentials = new HashMap<String, String>();
		this.userCredentials.put("user1", "secret1");
		this.userCredentials.put("user2", "secret2");
		
		this.userQueues = new HashMap<String, String[]>();
		this.userQueues.put("user1", new String[]{"QUEUE.0001"});
		this.userQueues.put("user2", new String[]{"QUEUE.0002"});
	}

	public QueueUserInfo getQueueUserInfo(String user, String password) throws SecurityException {
		String pwd = this.userCredentials.get(user);
		if(pwd == null || !pwd.equals(password)){
			throw new SecurityException("Invalid user name or password"); 
		}
		return new QueueUserInfo(user, this.userQueues.get(user));
	}

	private HashMap<String,String> userCredentials;
	private HashMap<String,String[]> userQueues;
}
