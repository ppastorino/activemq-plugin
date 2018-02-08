package samples.activemq.security.plugins;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.security.AuthorizationBroker;

public class CustomAuthorizationPlugin implements BrokerPlugin {

	public Broker installPlugin(Broker broker) {
    	return new AuthorizationBroker(broker, 	new CustomAuthorizationMap(config));
	
	}		
		
	public CustomAuthenticationConfig getConfig() {
		return config;
	}
	public void setConfig(CustomAuthenticationConfig config) {
		this.config = config;
	}

	private CustomAuthenticationConfig config; 
		
	
}
