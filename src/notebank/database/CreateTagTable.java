package notebank.database;
/**
 * Adapted from @author Ramesh Fadatare example
 * https://www.sourcecodeexamples.net/2019/11/java-jdbc-connection-to-hsqldb-database.html
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTagTable {

	private static final String createTableSQL = "CREATE TABLE Tags (\r\n" + 
			"  TagID  INT(255) UNSIGNED PRIMARY KEY AUTO_INCREMENT,\r\n" +
	        "  Name VARCHAR(50));";
	
	public void createTable() {
		System.out.println(createTableSQL);
		// Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
            // Step 2:Create a statement using connection object
            Statement statement = connection.createStatement();) {

            // Step 3: Execute the query or update query
            statement.execute(createTableSQL);

        } catch (SQLException e) {
            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }
	}
}
