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
import notebank.database.DeleteTag;
import notebank.database.InsertTag;
import notebank.database.JDBCUtils;

public class DeleteTagTest {

	static DeleteTag tagDeleter;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		CreateTagTable tableCreator = new CreateTagTable();		
		tableCreator.createTable();
		
		InsertTag tagInserter = new InsertTag();
		
		tagInserter.insertTag("school");
		tagInserter.insertTag("movie");
		
		tagDeleter = new DeleteTag();
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
		int idToDelete = rs.getInt(1);
		
		tagDeleter.deleteTag(idToDelete);
		
		query = "SELECT COUNT(*) FROM TAGS;";
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);	
		rs.next();
		
		int count = rs.getInt(1);
		
		query = "SELECT MIN(TAGID) FROM TAGS;";
		
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		
		rs.next();
		int remainingId = rs.getInt(1);
		
		assertEquals(1, count);
		assertNotEquals(remainingId, idToDelete);
	}

}
