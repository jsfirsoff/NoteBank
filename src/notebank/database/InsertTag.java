package notebank.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertTag {
	
	private static final String INSERT_TAGS_SQL = "INSERT INTO tags (name) VALUES (?);";
	
	public int insertTag(String name) throws SQLException {
		System.out.println(INSERT_TAGS_SQL);
		 // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
        	// Step 2:Create a statement using connection object
        	PreparedStatement statement = connection.prepareStatement(INSERT_TAGS_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);

            System.out.println(statement);
            // Step 3: Execute the query or update query
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            //return id to make things easier?
             rs.next();
             int id = rs.getInt(1);
             return id;
        } catch (SQLException e) {

            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }
        return -1;
        // Step 4: try-with-resource statement will auto close the connection.
	}
}
