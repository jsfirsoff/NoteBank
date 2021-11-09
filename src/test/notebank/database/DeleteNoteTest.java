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
import notebank.database.DeleteNote;
import notebank.database.InsertNote;
import notebank.database.JDBCUtils;
import notebank.tools.FileUtility;

public class DeleteNoteTest {
	
	static DeleteNote noteDeleter;

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
		FileUtility.writeToFile(file2, "Hello");
		file2.deleteOnExit();
		
		String path = file.getAbsolutePath();
		String path2 = file2.getAbsolutePath();
		
		noteInserter.insertNote("testFile", path, true);
		noteInserter.insertNote("testFile2", path2, false);
		
		noteDeleter = new DeleteNote();
	}
	
	@Test
	public void testDeleteNote() throws SQLException {
		
		String query = "SELECT MIN(NOTEID) FROM NOTES";
		
		Connection connection = JDBCUtils.getConnection();
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);	
		
		rs.next();	
		int idToDelete = rs.getInt(1);
		
		noteDeleter.deleteNote(idToDelete);
		
		query = "SELECT COUNT(*) FROM NOTES;";
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		rs.next();
		
		int count = rs.getInt(1);
		
		query = "SELECT MIN(NOTEID) FROM NOTES;";
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		
		rs.next();
		int remainingId = rs.getInt(1);
		
		assertEquals(1, count);
		assertNotEquals(remainingId, idToDelete);
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
