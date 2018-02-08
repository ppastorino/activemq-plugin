package samples.activemq.security.plugins;


public interface QueueAuthenticationService {

	public QueueUserInfo getQueueUserInfo(String user, String password) throws SecurityException;
}
