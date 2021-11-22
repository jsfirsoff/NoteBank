package test.notebank.model;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
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
import notebank.model.SearchNotesByDate;
import notebank.tools.FileUtility;

public class SearchNotesByDateTest {
	
	static Date date;
	
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
		
		SearchNotes search = new SearchNotes(new SearchNotesByDate(Date.valueOf(LocalDate.now())));
		ArrayList<Note> notes = search.getNotes(new FindNotes());
		
		assertEquals(1, notes.size());
		
		search = new SearchNotes(new SearchNotesByDate(Date.valueOf(LocalDate.of(2000, 12, 11))));
		notes = search.getNotes(new FindNotes());
		
		assertTrue(notes.isEmpty());
	}

}
