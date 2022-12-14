package Group5Project.WebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class WebAppApplication extends SpringBootServletInitializer {

	public static Connection connection;

	public static void main(String[] args) throws SQLException {

		SpringApplication.run(WebAppApplication.class, args);

		String jdbcUrl = "jdbc:sqlite:EasyServ_Database.db";
		connection = DriverManager.getConnection(jdbcUrl);

	}

}
