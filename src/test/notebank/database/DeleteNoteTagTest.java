package test.notebank.database;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import notebank.database.CreateNoteTable;
import notebank.database.CreateNoteTagsTable;
import notebank.database.CreateTagTable;
import notebank.database.DeleteNoteTag;
import notebank.database.InsertNote;
import notebank.database.InsertNoteTag;
import notebank.database.InsertTag;
import notebank.database.JDBCUtils;
import notebank.database.SelectNoteTag;
import notebank.model.Note;
import notebank.model.Tag;
import notebank.tools.FileUtility;

public class DeleteNoteTagTest {
	
	static DeleteNoteTag noteTagDeletor;
	static SelectNoteTag noteTagSelector;
	static LinkedList<Integer> noteIds = new LinkedList<Integer>();
	static LinkedList<Integer> tagIds = new LinkedList<Integer>();
	
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
		noteTagDeletor = new DeleteNoteTag();
		noteTagSelector = new SelectNoteTag();
		
		noteIds = new LinkedList<Integer>();
		tagIds = new LinkedList<Integer>();
		
		nTableCreator.createTable();
		tTableCreator.createTable();
		ntTableCreator.createTable();
		
		File file = tFolder.newFile("testFile");  //filename = title
		FileUtility.writeToFile(file, "hello");
		file.deleteOnExit();
		
		String path = file.getAbsolutePath();
		
		int noteid = noteInserter.insertNote("testFile", path, true);
		int tagid = tagInserter.insertTag("misc");
		
		noteIds.add(noteid);
		tagIds.add(tagid);
		
		noteTagInserter.insertNoteTag(noteid, tagid);
		
		file = tFolder.newFile("aFile");  //filename = title
		FileUtility.writeToFile(file, "hi");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("aFile", path, true);
		tagid = tagInserter.insertTag("media");
		
		noteIds.add(noteid);
		tagIds.add(tagid);
		
		noteTagInserter.insertNoteTag(noteid, tagid);
		
		file = tFolder.newFile("anotherFile");  //filename = title
		FileUtility.writeToFile(file, "sup");
		file.deleteOnExit();
		
		path = file.getAbsolutePath();
		
		noteid = noteInserter.insertNote("anotherFile", path, true);
		int tagid2 = tagInserter.insertTag("important");
		
		noteIds.add(noteid);
		tagIds.add(tagid2);
		
		noteTagInserter.insertNoteTag(noteid, tagid);
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
		int tagId = tagIds.getFirst();
		int noteId = noteIds.getFirst();
		
		noteTagDeletor.deleteNoteAndTags(noteId);
		
		ArrayList<Note> notes = noteTagSelector.selectNotesByTag(tagId);
		ArrayList<Tag> tags = noteTagSelector.selectTagsByNote(noteId);

		assertTrue(notes.isEmpty());
		assertTrue(tags.isEmpty());
		
		tagId = tagIds.get(1);
		noteId = noteIds.getLast();
		
		noteTagDeletor.deleteOneNoteTag(noteId, tagId);
		
		tags = noteTagSelector.selectTagsByNote(noteId);
		
		assertEquals(1, tags.size());
	}
}
