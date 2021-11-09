package notebank.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateNoteTable {
	// go over this
	private static final String createTableSQL = "CREATE TABLE Notes (\r\n" + 
			"  NoteID  INT(255) UNSIGNED PRIMARY KEY AUTO_INCREMENT,\r\n" +
	        "  Title VARCHAR(100),\r\n" + "  Text CLOB,\r\n" +
	        "  Pin BOOLEAN DEFAULT 'FALSE',\r\n" + "Date DATE DEFAULT CURRENT_DATE\r\n" + "  );";
			
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
