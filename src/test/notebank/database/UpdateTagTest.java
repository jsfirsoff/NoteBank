package test.notebank.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import notebank.database.CreateTagTable;
import notebank.database.InsertTag;
import notebank.database.JDBCUtils;
import notebank.database.SelectTag;
import notebank.database.UpdateTag;
import notebank.model.Tag;

public class UpdateTagTest {
	
	static UpdateTag tagUpdater;
	static SelectTag tagSelector;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CreateTagTable tableCreator = new CreateTagTable();		
		tableCreator.createTable();
		
		InsertTag tagInserter = new InsertTag();
		
		tagInserter.insertTag("school");
		tagInserter.insertTag("movie");
		
		tagSelector = new SelectTag();
		tagUpdater = new UpdateTag();
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
	public void test() throws SQLException {
		String query = "SELECT MIN(TAGID) FROM TAGS";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);	
		
		rs.next();	
		int idToUpdate = rs.getInt(1);
		
		tagUpdater.updateTag(idToUpdate, "music");
		
		Tag updatedTag = tagSelector.selectTag(idToUpdate);
		
		assertEquals("music", updatedTag.getName());
		assertEquals(idToUpdate, updatedTag.getId());
		
		query = "SELECT MAX(TAGID) FROM TAGS";
		
		connection = JDBCUtils.getConnection();
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		
		rs.next();	
		int id = rs.getInt(1);
		
		Tag tag = tagSelector.selectTag(id);
		
		assertEquals("movie", tag.getName());
		assertNotEquals(id, updatedTag);
	}

}
