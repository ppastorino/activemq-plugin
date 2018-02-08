package samples.activemq.security.plugins;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.security.MessageAuthorizationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomMessageAuthorizationPolicy implements
		MessageAuthorizationPolicy {

	public boolean isAllowedToConsume(ConnectionContext context, Message message) {
		ActiveMQDestination dest = message.getDestination();

		if (dest.isTopic()) {
			return true;
		}
		String userName = context.getClientId();
		log.info("client ID: " + userName);
		for (String path : dest.getDestinationPaths()) {
			if (path.equals(userName)) {
				return true;
			}
		}

		return false;

	}

	private final Logger log = LoggerFactory.getLogger(getClass());
}
