package notebank.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import notebank.tools.DateUtility;
import notebank.tools.FileUtility;

public class InsertNote {
	
	private static final String INSERT_NOTES_SQL = "INSERT INTO notes" +
	        "  (title, text, pin, date) VALUES " +
	        " (?, ?, ?, ?);";

	public int insertNote(String title, String filename, boolean pin) throws SQLException {
		System.out.println(INSERT_NOTES_SQL);
		 // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
        	// Step 2:Create a statement using connection object
        	PreparedStatement statement = connection.prepareStatement(INSERT_NOTES_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, title);
            statement.setClob(2, FileUtility.getFileReader(filename));
            statement.setBoolean(3, pin);
            statement.setDate(4, DateUtility.getSQLDate());
            

            System.out.println(statement);
            // Step 3: Execute the query or update query
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
           // ResultSet rs = statement.getResultSet(); //return id to make things easier?
            rs.next();
            int id = rs.getInt(1);
            //System.out.println(id);
            //return row;
            return id;
            
        } catch (SQLException e) {

            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }
        return -1;
        // Step 4: try-with-resource statement will auto close the connection.
	}
}
