package esrinea.gss.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;



public class DatabaseSessionFactory {
	private static SessionFactory sessionFactoryInstance = null;

	private DatabaseSessionFactory() {
		
	}

	public static SessionFactory getSessionFactory() {
		if(sessionFactoryInstance == null) {
			sessionFactoryInstance = new Configuration().configure().buildSessionFactory();
			}
			return sessionFactoryInstance;
	}

	
	
}
