package test.notebank.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import notebank.database.CreateTagTable;
import notebank.database.InsertTag;
import notebank.database.JDBCUtils;

public class InsertTagTest {
	
	static InsertTag tagInserter;
	
	@Rule
	public TemporaryFolder tFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CreateTagTable tableCreator = new CreateTagTable();
		tableCreator.createTable();
		
		tagInserter = new InsertTag();
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
	public void insertTagTest() throws SQLException {
		tagInserter.insertTag("school");
		
		String query = "SELECT * FROM TAGS;";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		rs.next();
		
		String name = rs.getString("NAME");
		
		assertEquals("school", name);
	}
	
	@Test
	public void uniqueIDTest() throws SQLException {
		tagInserter.insertTag("movie");
		
		String query = "SELECT * FROM TAGS;";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		int[] ids = new int[2];
		int i = 0;
		while (rs.next()) {
			ids[i++] = rs.getInt("TAGID");
		}
		
		assertNotEquals(ids[0], ids[1]);
	}

}
