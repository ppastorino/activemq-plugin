package samples.activemq.security.plugins;

public class QueueUserInfo {

	public QueueUserInfo(String userName, String[] authorizedQueues) {
		super();
		this.userName = userName;
		this.authorizedQueues = authorizedQueues;
	}

	public String getUserName() {
		return userName;
	}

	public String[] getAuthorizedQueues() {
		return authorizedQueues;
	}

	private String[] authorizedQueues;
	private String userName;
}
