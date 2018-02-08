package samples.activemq.security.plugins;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;

public class CustomAthenticationPlugin implements BrokerPlugin {

	public Broker installPlugin(Broker broker) throws Exception {
		return new CustomAuthenticationBroker(broker, this.authenticationService, this.config);
	}

	public CustomAuthenticationConfig getConfig() {
		return config;
	}

	public void setConfig(CustomAuthenticationConfig config) {
		this.config = config;
	}

	public void setAuthenticationService(QueueAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public QueueAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	private QueueAuthenticationService authenticationService;
	private CustomAuthenticationConfig config;

}
