package kambu.rekrutacja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "kambu")
@SpringBootApplication
public class RekrutacjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RekrutacjaApplication.class, args);
	}

}
