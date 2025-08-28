package iuh.fit.se.L09_SpringSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

//	@PostConstruct
//	static void in(){
//		Dotenv dotenv = Dotenv.load();
//		String api = dotenv.get("APP_SECRET_KEY");
//		System.out.println(api);
//	}
	public static void main(String[] args) {
//		in();
		SpringApplication.run(Main.class, args);
	}

}
