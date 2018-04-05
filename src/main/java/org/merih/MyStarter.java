/**
 * 
 */
package org.merih;


/**
 * @author Merih
 *
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class MyStarter {



 
	public static void main(String[] args)    throws Exception {
		SpringApplication.run(MyStarter.class,   
				args);
	}
}
