/**
 * 
 */
package org.merih;


/**
 * @author Merih
 *
 */

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

//@EnableJpaAuditing
//@EnableJpaRepositories
@SpringBootApplication
@EnableAutoConfiguration
public class MyStarter {



 
	public static void main(String[] args)    throws Exception {
		SpringApplication.run(MyStarter.class,   
				args);
	}
}
