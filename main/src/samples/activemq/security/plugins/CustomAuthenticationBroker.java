package samples.activemq.security.plugins;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.jaas.GroupPrincipal;
import org.apache.activemq.security.AbstractAuthenticationBroker;
import org.apache.activemq.security.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthenticationBroker extends AbstractAuthenticationBroker {

	public CustomAuthenticationBroker(Broker next, 
			QueueAuthenticationService authenticationService,
			CustomAuthenticationConfig config) {
		super(next);
		this.authenticationService = authenticationService;
		this.config =  config;
	}

	public void addConnection(ConnectionContext context, ConnectionInfo info)
			throws Exception {

		final String username = info.getUserName();
		final String password = info.getPassword();
		final TransportConnector tc = (TransportConnector) context
				.getConnection().getConnector();
		
		log.debug("Usuer " + username + " connecting...");
		
		for (String pe : config.getProtocolExceptions()) {

			if (tc.getName().equals(pe)) {
				context.setSecurityContext(createSecurityContext4Admin(info
						.getUserName()));
				super.addConnection(context, info);
				log.info("Usuario " + username + " conectado como Administrador");
				return;
			}
		}

		final SecurityContext sc = authenticate(username, password,
				(X509Certificate[]) info.getTransportContext());
		context.setSecurityContext(sc);
			
		
		super.addConnection(context, info);
	}

	public SecurityContext authenticate(final String username, String password,
			X509Certificate[] certificates) throws SecurityException {

		SecurityContext securityContext = null;

		if (username == null || password == null) {
			final String errorMessage = "User name or password not specified";
			log.error(
					errorMessage);
			throw new SecurityException(errorMessage);
		} else {
			securityContext = createContex4user(this.authenticationService.getQueueUserInfo(username, password));
		}
		log.info("User " + username + " authenticated");

		return securityContext;

	}
	
	private boolean isAdmin(Set<Principal> principals) {
		if (principals == null) {
			return false;
		}
		for (Principal p : principals) {
			if (config.getAdminGroup().equals(p.getName())) {
				return true;
			}
		}
		return false;
	}
	

	private SecurityContext createSecurityContext4Admin(final String userName) {

		SecurityContext securityContext = new SecurityContext(userName) {
			@Override
			public Set<Principal> getPrincipals() {
				Set<Principal> groups = new HashSet<Principal>();
				groups.add(new GroupPrincipal(config.getAllGroup()));
				groups.add(new GroupPrincipal(config.getAdminGroup()));
				groups.add(new GroupPrincipal(userName));

				return groups;
			}
		};

		return securityContext;
	}
	
	
	public SecurityContext createContex4user(final QueueUserInfo info) {
		SecurityContext sc = new SecurityContext(info.getUserName()) {
			@Override
			public Set<Principal> getPrincipals() {
				Set<Principal> groups = new HashSet<Principal>();
				for (String queueName : info.getAuthorizedQueues()) {
					log.info("Adding group with queue name : "
									+ queueName
									+ " for user "
									+ info.getUserName());
					groups.add(new GroupPrincipal(queueName));
				}

				groups.add(new GroupPrincipal(config.getAllGroup()));

				return groups;
			}

		};
		return sc;
	}

	
	private QueueAuthenticationService authenticationService;
	private Logger log = LoggerFactory.getLogger(getClass());
	private CustomAuthenticationConfig config;
}
