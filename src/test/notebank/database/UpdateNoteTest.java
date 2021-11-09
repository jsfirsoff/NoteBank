package test.notebank.database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
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
import notebank.database.InsertNote;
import notebank.database.JDBCUtils;
import notebank.database.SelectNote;
import notebank.database.UpdateNote;
import notebank.model.Note;
import notebank.tools.FileUtility;

public class UpdateNoteTest {
	
	static UpdateNote noteUpdater;
	static SelectNote noteSelector;

	@ClassRule
	public static TemporaryFolder tFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUp() throws SQLException, IOException {
		
		CreateNoteTable tableCreator = new CreateNoteTable();
		tableCreator.createTable();
		
		InsertNote noteInserter = new InsertNote();
		
		String filename = "testFile.txt";
		String filename2 = "testFile2.txt";
		
		File file = tFolder.newFile(filename); 
		FileUtility.writeToFile(file, "Hello");
		file.deleteOnExit();
		
		File file2 = tFolder.newFile(filename2); 
		FileUtility.writeToFile(file2, "Hi");
		file2.deleteOnExit();
		
		String path = file.getAbsolutePath();
		String path2 = file2.getAbsolutePath();
		
		noteInserter.insertNote("testFile", path, true);
		noteInserter.insertNote("testFile2", path2, false);
		
		noteUpdater = new UpdateNote();
		noteSelector = new SelectNote();
	}
	
	@Test
	public void testUpdateNote() throws SQLException, IOException {
		String query = "SELECT MIN(NOTEID) FROM NOTES";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);	
		
		rs.next();	
		int idToUpdate = rs.getInt(1);
		
		String filename = "tf.txt";
		
		File file = tFolder.newFile(filename); 
		FileUtility.writeToFile(file, "Hello world");
		file.deleteOnExit();
		
		String path = file.getAbsolutePath();
		
		noteUpdater.updateNote(idToUpdate, "tf", path, false);
		
		Note updatedNote = noteSelector.selectNote(idToUpdate);
		
		assertEquals("Hello world", updatedNote.getText());
		assertEquals("tf", updatedNote.getTitle());
		assertEquals(idToUpdate, updatedNote.getId());
		assertFalse(updatedNote.isPin());
		
		query = "SELECT MAX(NOTEID) FROM NOTES";
		
		connection = JDBCUtils.getConnection();
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		
		rs.next();	
		int id = rs.getInt(1);
		
		Note note = noteSelector.selectNote(id);
		
		assertEquals("Hi", note.getText());
		assertEquals("testFile2", note.getTitle());
		assertEquals(id, note.getId());
		assertFalse(note.isPin());
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
