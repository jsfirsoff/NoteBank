package notebank.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTag {
	private static final String delete_SQL = "DELETE FROM tags WHERE tagid = ?";

	 public void deleteTag(int id) throws SQLException {

	        System.out.println(delete_SQL);
	        // Step 1: Establishing a Connection
	        try (Connection connection = JDBCUtils.getConnection();
	            // Step 2:Create a statement using connection object
	            PreparedStatement statement = connection.prepareStatement(delete_SQL);) {
	        	statement.setInt(1, id);
	            // Step 3: Execute the query or update query
	            statement.executeUpdate();

	        } catch (SQLException e) {
	            // print SQL exception information
	            JDBCUtils.printSQLException(e);
	        }
	    }
}
