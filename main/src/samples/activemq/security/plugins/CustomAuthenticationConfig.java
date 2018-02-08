package samples.activemq.security.plugins;

import java.util.List;

public class CustomAuthenticationConfig {

	private static final String ALL_GROUP_DEFAULT_NAME = "all";
	private static final String ADMIN_GROUP_DEFAULT_NAME = "admins";
	private static final String QUEUE_PATERN = "AG.%04d";

	public String getAllGroup() {
		return allGroup;
	}

	public void setAllGroup(String allGroup) {
		this.allGroup = allGroup;
	}

	public String getAdminGroup() {
		return adminGroup;
	}

	public void setAdminGroup(String adminGroup) {
		this.adminGroup = adminGroup;
	}

	public String getQueuePatern() {
		return queuePatern;
	}

	public void setQueuePatern(String queuePatern) {
		this.queuePatern = queuePatern;
	}

	public List<String> getProtocolExceptions() {
		return protocolExceptions;
	}

	public void setProtocolExceptions(List<String> protocolExceptions) {
		this.protocolExceptions = protocolExceptions;
	}

	private String allGroup = ALL_GROUP_DEFAULT_NAME;
	private String adminGroup = ADMIN_GROUP_DEFAULT_NAME;
	private String queuePatern = QUEUE_PATERN;
	private List<String> protocolExceptions;

}
