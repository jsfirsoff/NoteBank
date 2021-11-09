package test.notebank.database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import notebank.database.CreateNoteTable;
import notebank.database.InsertNote;
import notebank.database.JDBCUtils;
import notebank.database.SelectNote;
import notebank.model.Note;
import notebank.tools.FileUtility;

public class SelectNoteTest {
	
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
		
		noteSelector = new SelectNote();
	}
	
	@Test
	public void testSelectNote() throws SQLException {
		String query = "SELECT MIN(NOTEID) FROM NOTES";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);	
		
		rs.next();
		int expectedId = rs.getInt(1);
		
		query = "SELECT * FROM NOTES WHERE NOTEID = " + expectedId + ";";
		
		connection = JDBCUtils.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		
		rs.next();
		String expectedTitle = rs.getString("TITLE");
		String expectedText = rs.getString("TEXT");
		boolean expectedPin = rs.getBoolean("PIN");
		Date expectedDate = rs.getDate("DATE");
		
		Note note = noteSelector.selectNote(expectedId);
		
		int id = note.getId();
		String title = note.getTitle();
		String text = note.getText();
		boolean pin = note.isPin();
		Date date = note.getDate();
		
		assertEquals(expectedId, id);
		assertEquals(expectedTitle, title);
		assertEquals(expectedText, text);
		assertEquals(expectedPin, pin);
		assertEquals(expectedDate, date);
	}
	
	@Test
	public void testSelectAllNotes() {
		ArrayList<Note> notes = noteSelector.selectAllNotes();
		
		assertEquals(2, notes.size());
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
