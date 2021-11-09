package test.notebank.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import notebank.database.CreateTagTable;
import notebank.database.InsertTag;
import notebank.database.JDBCUtils;
import notebank.database.SelectTag;
import notebank.model.Tag;

public class SelectTagTest {
	
	static SelectTag tagSelector;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		CreateTagTable tableCreator = new CreateTagTable();		
		tableCreator.createTable();
		
		InsertTag tagInserter = new InsertTag();
		
		tagInserter.insertTag("school");
		tagInserter.insertTag("stuff");
		
		tagSelector = new SelectTag();
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
	public void testSelectTag() throws SQLException {
		String query = "SELECT MIN(TAGID) FROM TAGS";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);	
		
		rs.next();
		int expectedId = rs.getInt(1);
		
		query = "SELECT * FROM TAGS WHERE TAGID = " + expectedId + ";";
		
		connection = JDBCUtils.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		
		rs.next();
		String expectedName = rs.getString("NAME");
		
		Tag tag = tagSelector.selectTag(expectedId);
		
		assertEquals(expectedId, tag.getId());
		assertEquals(expectedName, tag.getName());
	}
	
	@Test
	public void testSelectAlltags() {
		ArrayList<Tag> tags = tagSelector.selectAllTags();
		
		assertEquals(2, tags.size());
	}

}
