package samples.activemq.security.plugins;

import java.util.HashSet;
import java.util.Set;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.jaas.GroupPrincipal;
import org.apache.activemq.security.AuthorizationMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthorizationMap implements AuthorizationMap {

	public CustomAuthorizationMap(CustomAuthenticationConfig config) {
		super();
		this.config = config;
}

	public Set<Object> getTempDestinationAdminACLs() {
		return null;
	}

	public Set<Object> getTempDestinationReadACLs() {
		return null;
	}

	public Set<Object> getTempDestinationWriteACLs() {
		return null;

	}

	public Set<Object> getAdminACLs(ActiveMQDestination paramActiveMQDestination) {
		log.info("Obteniendo permisos de Admin para: "
				+ paramActiveMQDestination.getPhysicalName());

		HashSet<Object> groups = new HashSet<Object>();
		groups.add(new GroupPrincipal(config.getAdminGroup()));
		if (paramActiveMQDestination.isTopic()) {
			groups.add(new GroupPrincipal(config.getAllGroup()));
		}

		return groups;

	}

	public Set<Object> getReadACLs(ActiveMQDestination paramActiveMQDestination) {
		log.info("Obteniendo permisos de Lectura para: "
				+ paramActiveMQDestination.getPhysicalName());

		HashSet<Object> groups = new HashSet<Object>();
		groups.add(new GroupPrincipal(config.getAdminGroup()));
		groups.add(new GroupPrincipal(paramActiveMQDestination
				.getPhysicalName()));
		if (paramActiveMQDestination.isTopic()) {
			groups.add(new GroupPrincipal(config.getAllGroup()));
		}
		return groups;
	}

	public Set<Object> getWriteACLs(ActiveMQDestination paramActiveMQDestination) {
		log.info("Obteniendo permisos de Escritura para: "
				+ paramActiveMQDestination.getPhysicalName());

		HashSet<Object> groups = new HashSet<Object>();
		groups.add(new GroupPrincipal(config.getAdminGroup()));
		groups.add(new GroupPrincipal(paramActiveMQDestination
				.getPhysicalName()));
		return groups;

	}

	public CustomAuthenticationConfig getConfig() {
		return config;
	}

	public void setConfig(CustomAuthenticationConfig config) {
		this.config = config;
	}

	private Logger log = LoggerFactory.getLogger(getClass());
	private CustomAuthenticationConfig config;
}