package test.notebank.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import notebank.database.CreateNoteTable;
import notebank.database.JDBCUtils;

public class CreateNoteTableTest {

	
	@BeforeClass
	public static void setUp() {
		
		CreateNoteTable tableCreator = new CreateNoteTable();
		tableCreator.createTable();
	}
	
	@Test
	public void createNotesTableTest() throws SQLException {
		
		Connection connection = JDBCUtils.getConnection();
		
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getTables(null, null, "NOTES", null);
		rs.next();
		String table = rs.getString(3); // third column to get table name
		
		assertTrue(table.equalsIgnoreCase("Notes"));
	}
	
	@Test
	public void checkColumnsTest() throws SQLException {
		
		LinkedList<String> expectedColumns = new LinkedList<String>(Arrays.asList(
				"NOTEID", "TITLE", "TEXT", "PIN", "DATE"));
		LinkedList<Integer> expectedColumnData = new LinkedList<Integer>(Arrays.asList(
				java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.CLOB, 
				java.sql.Types.BOOLEAN, java.sql.Types.DATE));

		Connection connection = JDBCUtils.getConnection();
		
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getColumns(null, null, "NOTES", null);
		
		LinkedList<String> columns = new LinkedList<String>();
		LinkedList<Integer> colData = new LinkedList<Integer>();
		String column;
		int data;
		
		while (rs.next()) {
			column = rs.getString(4); // fourth column to get column names
			columns.add(column);
			data = rs.getInt(5); // fifth column to get data type
			colData.add(data);
		}
		
		assertEquals(expectedColumns, columns);
		assertEquals(expectedColumnData, colData);
	}
	
	@AfterClass
	public static void tearDown() throws SQLException {
		
		String query = "DROP TABLE NOTES;";
		
		Connection connection = JDBCUtils.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);

		System.out.println("Dropping table notes...");
		statement.execute();
		System.out.println("Closing connection...");
		connection.close();
	}
}
