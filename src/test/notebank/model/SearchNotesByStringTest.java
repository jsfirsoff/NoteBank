package test.notebank.model;

import static org.junit.Assert.*;

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
import notebank.model.SearchNotesByString;
import notebank.tools.FileUtility;

public class SearchNotesByStringTest {
	
	static String searchstring = "Test";
	static String searchstring2 = "HELLO";
	static String searchstring3 = "";
	static String searchstring4 = "test hello";
	static String searchstring5 = "'test hello'";
	static String searchstring6 = "\"test hello\"";
	static String searchstring7 = "'sup dude'";
	
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
		
		File file = tFolder.newFile("test");  //filename = title
		FileUtility.writeToFile(file, "hello");
		file.deleteOnExit();
		
		String path = file.getAbsolutePath();
		
		int noteid = noteInserter.insertNote("test", path, true);
		int tagid = tagInserter.insertTag("misc");
			
		noteTagInserter.insertNoteTag(noteid, tagid);
		
		file = tFolder.newFile("aFile");  //filename = title
		FileUtility.writeToFile(file, "hi. sup dude?");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("aFile", path, true);
		int tagid2 = tagInserter.insertTag("media");
		
		noteTagInserter.insertNoteTag(noteid, tagid2);
		
		file = tFolder.newFile("anotherFile");  //filename = title
		FileUtility.writeToFile(file, "test");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("anotherFile", path, true);
		
		noteTagInserter.insertNoteTag(noteid, tagid);
		noteTagInserter.insertNoteTag(noteid, tagid2);
		
		file = tFolder.newFile("yetanotherfile");  //filename = title
		FileUtility.writeToFile(file, "test hello.");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("yetanotherfile", path, true);
		
		noteTagInserter.insertNoteTag(noteid, tagid2);
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
		SearchNotes search = new SearchNotes(new SearchNotesByString(searchstring));
		ArrayList<Note> notes = search.getNotes(new FindNotes());
		
		assertEquals(3, notes.size());
		
		search = new SearchNotes(new SearchNotesByString(searchstring2));
		notes = search.getNotes(new FindNotes());
		
		assertEquals(2, notes.size());
		
		search = new SearchNotes(new SearchNotesByString(searchstring4));
		notes = search.getNotes(new FindNotes());
		
		assertEquals(3, notes.size());
		
		search = new SearchNotes(new SearchNotesByString(searchstring5));
		notes = search.getNotes(new FindNotes());
		
		assertEquals(1, notes.size());
		
		search = new SearchNotes(new SearchNotesByString(searchstring6));
		notes = search.getNotes(new FindNotes());
		
		assertEquals(1, notes.size());
		
		search = new SearchNotes(new SearchNotesByString(searchstring7));
		notes = search.getNotes(new FindNotes());
		
		assertEquals(1, notes.size());
	}

}
