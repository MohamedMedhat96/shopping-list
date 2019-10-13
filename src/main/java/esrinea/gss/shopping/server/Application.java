package esrinea.gss.shopping.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = { "esrinea.gss.general","esrinea.gss.shopping.report","esrinea.gss.shopping.category","esrinea.gss.shopping.item","esrinea.gss.shopping.operation"} )
@EnableJpaRepositories(basePackages = { "esrinea.gss.general","esrinea.gss.shopping.report","esrinea.gss.shopping.category","esrinea.gss.shopping.item","esrinea.gss.shopping.operation"})
public class Application {


	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	
	}

}