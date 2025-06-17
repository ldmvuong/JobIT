package fit.hcmute.JobIT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//disable security
//@SpringBootApplication(exclude = {
//		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
//})


@SpringBootApplication
public class JobItApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobItApplication.class, args);
	}

}
