package br.com.application.util.mail;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class MailConfigProducer {

	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException {
		
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/mail.properties"));
		
		SimpleMailConfig simpleMailConfig = new SimpleMailConfig();
		simpleMailConfig.setEnableSsl(Boolean.parseBoolean(properties.getProperty("mail.enable.ssl")));
		simpleMailConfig.setServerPort(Integer.parseInt(properties.getProperty("mail.server.port")));
		simpleMailConfig.setAuth(Boolean.parseBoolean(properties.getProperty("mail.auth")));
		simpleMailConfig.setServerHost(properties.getProperty("mail.server.host"));
		simpleMailConfig.setUsername(properties.getProperty("mail.username"));
		simpleMailConfig.setPassword(properties.getProperty("mail.password"));
		
		return simpleMailConfig;
	}
	
}
