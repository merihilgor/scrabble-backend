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
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class MyStarter {



 
	public static void main(String[] args)    throws Exception {
		SpringApplication.run(MyStarter.class,   
				args);
	}
}
