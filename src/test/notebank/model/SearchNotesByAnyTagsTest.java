package test.notebank.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
import notebank.model.FindNotes;
import notebank.model.Note;
import notebank.model.SearchNotes;
import notebank.model.SearchNotesByAnyTags;
import notebank.tools.FileUtility;

public class SearchNotesByAnyTagsTest {
	
	static ArrayList<String> searchtags = new ArrayList<String>();
	
	@ClassRule
	public static TemporaryFolder tFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CreateTagTable tTableCreator = new CreateTagTable();
		CreateNoteTable nTableCreator = new CreateNoteTable();
		CreateNoteTagsTable ntTableCreator = new CreateNoteTagsTable();
		InsertNote noteInserter = new InsertNote();
		InsertTag tagInserter = new InsertTag();
		InsertNoteTag noteTagInserter = new InsertNoteTag();
		
		nTableCreator.createTable();
		tTableCreator.createTable();
		ntTableCreator.createTable();
		
		File file = tFolder.newFile("testFile");  //filename = title
		FileUtility.writeToFile(file, "hello");
		file.deleteOnExit();
		
		String path = file.getAbsolutePath();
		
		int noteid = noteInserter.insertNote("testFile", path, true);
		int tagid = tagInserter.insertTag("misc");
		
		searchtags.add("misc");
		
		noteTagInserter.insertNoteTag(noteid, tagid);
		
		file = tFolder.newFile("aFile");  //filename = title
		FileUtility.writeToFile(file, "hi");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("aFile", path, true);
		tagid = tagInserter.insertTag("media");

		searchtags.add("media");
		
		noteTagInserter.insertNoteTag(noteid, tagid);
		
		file = tFolder.newFile("anotherFile");  //filename = title
		FileUtility.writeToFile(file, "sup");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("anotherFile", path, true);
		tagid = tagInserter.insertTag("important");
		
		noteTagInserter.insertNoteTag(noteid, tagid);
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
	public void test() {
		SearchNotes search = new SearchNotes(new SearchNotesByAnyTags(searchtags));
		ArrayList<Note> notes = search.getNotes(new FindNotes());
		
		assertEquals(2, notes.size());
	}

}
