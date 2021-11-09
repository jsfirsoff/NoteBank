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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import notebank.database.CreateNoteTable;
import notebank.database.InsertNote;
import notebank.database.JDBCUtils;
import notebank.tools.DateUtility;
import notebank.tools.FileUtility;

public class InsertNoteTest {
	
	static InsertNote noteInserter;
	
	@Rule
	public TemporaryFolder tFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUp() {
		
		CreateNoteTable tableCreator = new CreateNoteTable();
		tableCreator.createTable();
		
		noteInserter = new InsertNote();
	}
	
	@Test
	public void insertNoteTest() throws IOException, SQLException {
		String expectedTitle = "testFile", expectedText = "Hello";
		Date expectedDate = DateUtility.getSQLDate();
		boolean expectedPin = true;
		String filename = "testFile.txt";
		
		File file = tFolder.newFile(filename);  //filename = title
		FileUtility.writeToFile(file, expectedText);
		file.deleteOnExit();
		
		String path = file.getAbsolutePath();
		
		noteInserter.insertNote(expectedTitle, path, true);
		
		// check if data is in table...
		
		String query = "SELECT * FROM NOTES;";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		rs.next();
		int id = rs.getInt("NOTEID");
		String title = rs.getString("TITLE");
		String text = rs.getString("TEXT");
		boolean pin = rs.getBoolean("PIN");
		Date date = rs.getDate("DATE");
		
		assertNotNull(id);
		assertEquals(expectedTitle, title);
		assertEquals(expectedText, text);
		assertEquals(expectedPin, pin);
		assertEquals(expectedDate, date);
	}
	
	@Test
	public void uniqueIDTest() throws IOException, SQLException {
		String filename = "testFile.txt";
		String filename2 = "testFile2.txt";
		String text = "Hello";
		
		File file = tFolder.newFile(filename); 
		FileUtility.writeToFile(file, text);
		file.deleteOnExit();
		
		File file2 = tFolder.newFile(filename2); 
		FileUtility.writeToFile(file2, text);
		file2.deleteOnExit();
		
		String path = file.getAbsolutePath();
		String path2 = file2.getAbsolutePath();
		
		noteInserter.insertNote(null, path, false);
		noteInserter.insertNote(null, path2, false);
		
		String query = "SELECT * FROM NOTES;";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		int[] ids = new int[3];
		int i = 0;
		while (rs.next()) {
			ids[i++] = rs.getInt("NOTEID");
		}
		
		assertNotEquals(ids[0], ids[1]);
		assertNotEquals(ids[0], ids[2]);
		assertNotEquals(ids[1], ids[2]);
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
