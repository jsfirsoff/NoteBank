package notebank.database;
/**
 * Adapted from @author Ramesh Fadatare example
 * https://www.javaguides.net/2018/10/jdbc-statement-update-record-example.html
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTag {
	private static final String UPDATE_SQL = "UPDATE tags SET name = ? WHERE tagid = ?;";

	public void updateTag(int id, String name) throws SQLException {
		System.out.println(UPDATE_SQL);
		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

			statement.setString(1, name);
			statement.setInt(2, id);

			// Step 3: Execute the query or update query
			statement.executeUpdate();
		} catch (SQLException e) {

			// print SQL exception information
			JDBCUtils.printSQLException(e);
		}

	}
}
