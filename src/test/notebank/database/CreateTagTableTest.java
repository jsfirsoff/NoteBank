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

import notebank.database.CreateTagTable;
import notebank.database.JDBCUtils;

public class CreateTagTableTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CreateTagTable tableCreator = new CreateTagTable();
		tableCreator.createTable();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		String query = "DROP TABLE TAGS;";
		
		Connection connection = JDBCUtils.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);

		System.out.println("Dropping table tags...");
		statement.execute();
		System.out.println("Closing connection...");
		connection.close();
	}

	@Test
	public void createTagsTableTest() throws SQLException {
		Connection connection = JDBCUtils.getConnection();
		
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getTables(null, null, "TAGS", null);
		rs.next();
		String table = rs.getString(3); // third column to get table name
		
		assertTrue(table.equalsIgnoreCase("Tags"));
	}
	
	@Test
	public void checkColumnsTest() throws SQLException {
		
		LinkedList<String> expectedColumns = new LinkedList<String>(Arrays.asList(
				"TAGID", "NAME"));
		LinkedList<Integer> expectedColumnData = new LinkedList<Integer>(Arrays.asList(
				java.sql.Types.INTEGER, java.sql.Types.VARCHAR));

		Connection connection = JDBCUtils.getConnection();
		
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getColumns(null, null, "TAGS", null);
		
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

}
