package test.notebank.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import notebank.database.JDBCUtils;

public class JDBCUtilsTest {

	@Test
	public void testConnection() throws SQLException {
		Connection connection = JDBCUtils.getConnection();
		
		assertNotNull(connection);
		assertTrue(connection.isValid(0));
	}

}
