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

		String jdbcUrl = "jdbc:sqlite:testDB.db";
		connection = DriverManager.getConnection(jdbcUrl);

//		String sql = "create table contacts (firstname varchar(20), lastname varchar(20))";
//		//String sql = "insert into contacts values ('fName', 'lName');
//		Statement statement = connection.createStatement();
//		statement.execute(sql);
	}

}
