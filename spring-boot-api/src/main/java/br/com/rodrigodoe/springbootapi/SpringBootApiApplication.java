package br.com.rodrigodoe.springbootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.rodrigodoe.springbootapi.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageConfig.class
})
@EnableAutoConfiguration
@ComponentScan
public class SpringBootApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApiApplication.class, args);
//		BCryptPasswordEncoder bCryptPasswordEncoder = BCryptPasswordEncoder(16);
//		String result = bCryptPasswordEncoder.encode("admin123");
//		System.out.println(result);
	}

}
