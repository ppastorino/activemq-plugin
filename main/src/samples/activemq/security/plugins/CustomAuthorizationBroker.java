package samples.activemq.security.plugins;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;

public class CustomAuthorizationBroker extends BrokerFilter {

	public CustomAuthorizationBroker(Broker next) {
		super(next);
		
	}

}
