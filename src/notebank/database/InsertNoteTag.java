package notebank.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertNoteTag {
	
	private static final String INSERT_NOTETAG_SQL = "INSERT INTO notetags" +
	        "  (noteid, tagid) VALUES (?, ?);";
	
	public void insertNoteTag(int noteid, int tagid) throws SQLException {
		System.out.println(INSERT_NOTETAG_SQL);
		 // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
        	// Step 2:Create a statement using connection object
        	PreparedStatement statement = connection.prepareStatement(INSERT_NOTETAG_SQL)) {

            statement.setInt(1, noteid);
            statement.setInt(2, tagid);

            System.out.println(statement);
            // Step 3: Execute the query or update query
            statement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
	}
}
