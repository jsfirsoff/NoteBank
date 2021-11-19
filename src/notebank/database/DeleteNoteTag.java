package notebank.database;
/**
 * Adapted from @author Ramesh Fadatare example
 * https://www.sourcecodeexamples.net/2019/11/jdbc-delete-query-example.html
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteNoteTag {
	private static final String NOTE_QUERY = "DELETE FROM notetags WHERE noteid =?";
	private static final String TAG_QUERY = "DELETE FROM notetags WHERE tagid =? AND noteid =?";

	public void deleteNoteAndTags(int noteId) {

        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(NOTE_QUERY);) {
            statement.setInt(1, noteId); // set index of parameter and parameter type
            System.out.println(statement);
            // Step 3: Execute the query or update query
            statement.executeUpdate();
    
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
	}

	public void deleteOneNoteTag(int noteId, int tagId) {
		
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(TAG_QUERY);) {
            statement.setInt(1, tagId); // set index of parameter and parameter type
            statement.setInt(2, noteId);
            System.out.println(statement);
            // Step 3: Execute the query or update query
            statement.executeUpdate();
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
	}
}
