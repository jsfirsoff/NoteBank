package test.notebank.database;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import notebank.database.CreateNoteTable;
import notebank.database.CreateNoteTagsTable;
import notebank.database.CreateTagTable;
import notebank.database.InsertNote;
import notebank.database.InsertNoteTag;
import notebank.database.InsertTag;
import notebank.database.JDBCUtils;
import notebank.database.SelectNote;
import notebank.database.SelectTag;
import notebank.model.Note;
import notebank.model.Tag;
import notebank.tools.FileUtility;

public class InsertNoteTagTest {

	static InsertNoteTag noteTagInserter;
	
	@ClassRule
	public static TemporaryFolder tFolder = new TemporaryFolder();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		CreateTagTable tTableCreator = new CreateTagTable();
		CreateNoteTable nTableCreator = new CreateNoteTable();
		CreateNoteTagsTable ntTableCreator = new CreateNoteTagsTable();
		InsertNote noteInserter = new InsertNote();
		InsertTag tagInserter = new InsertTag();
		
		nTableCreator.createTable();
		tTableCreator.createTable();
		ntTableCreator.createTable();
		
		File file = tFolder.newFile("testFile");  //filename = title
		FileUtility.writeToFile(file, "hello");
		file.deleteOnExit();
		
		String path = file.getAbsolutePath();
		
		noteInserter.insertNote("testFile", path, true);
		tagInserter.insertTag("misc");
		
		noteTagInserter = new InsertNoteTag();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		String query = "DROP TABLE NOTES,TAGS,NOTETAGS;";
		
		Connection connection = JDBCUtils.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);

		System.out.println("Dropping all tables...");
		statement.execute();
		System.out.println("Closing connection...");
		connection.close();
	}

	@Test
	public void testInsertNoteTag() throws SQLException {
		
		String query = "SELECT * FROM NOTES;";
		Connection connection = JDBCUtils.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		rs.next();
		int noteId = rs.getInt("NOTEID");
		
		SelectNote noteSelector = new SelectNote();	
		Note note = noteSelector.selectNote(noteId);
		
		query = "SELECT * FROM TAGS;";
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		rs.next();
		int tagId = rs.getInt("TAGID");

		SelectTag tagSelector = new SelectTag();
		Tag tag = tagSelector.selectTag(tagId);
		
		noteTagInserter.insertNoteTag(noteId, tagId);
		
		
		query = "SELECT COUNT(*) FROM NOTETAGS;";
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		rs.next();	
		int count = rs.getInt(1);
		
		assertEquals(1, count);
		
		
		query = "SELECT * FROM NOTETAGS;";
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		
		rs.next();
		int tId = rs.getInt("TAGID");
		int nId = rs.getInt("NOTEID");
		
		note = noteSelector.selectNote(nId);
		tag = tagSelector.selectTag(tId);
		
		assertEquals("testFile", note.getTitle());
		assertEquals("misc", tag.getName());
	}

}
