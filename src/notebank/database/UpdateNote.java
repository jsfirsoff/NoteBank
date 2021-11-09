package notebank.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import notebank.tools.DateUtility;
import notebank.tools.FileUtility;

public class UpdateNote {
	
	private static final String UPDATE_SQL = "UPDATE notes SET title = ?,\r\n" + " text = ?,\r\n"
					+ " pin = ?,\r\n" + " date = ?\r\n WHERE noteid = ?;";

	public void updateNote(int id, String title, String filename, boolean pin) throws SQLException {
		System.out.println(UPDATE_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
        	
            statement.setString(1, title);
            statement.setClob(2, FileUtility.getFileReader(filename));
            statement.setBoolean(3, pin);
            statement.setDate(4, DateUtility.getSQLDate());
            statement.setInt(5, id);

            // Step 3: Execute the query or update query
            statement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }
// how do tags fit in?
        // Step 4: try-with-resource statement will auto close the connection.
    }

}
