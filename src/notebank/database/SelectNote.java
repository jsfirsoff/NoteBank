package notebank.database;
/**
 * Adapted from @author Ramesh Fadatare example
 * https://www.sourcecodeexamples.net/2019/11/jdbc-select-query-example.html
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import notebank.model.Note;
import notebank.model.Tag;

public class SelectNote {
	private static final String QUERY = "SELECT noteid,title,text,pin,date FROM notes WHERE noteid =?";
	private static final String QUERY_ALL = "SELECT * FROM notes";

	public Note selectNote(int targetId) {
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(QUERY);) {
            statement.setInt(1, targetId); // set index of parameter and parameter type
            System.out.println(statement);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            rs.next();
            int id = rs.getInt("NOTEID");
            String title = rs.getString("TITLE");
            String text = rs.getString("TEXT");
            boolean pin = rs.getBoolean("PIN");
            Date date = rs.getDate("DATE");
            
            SelectNoteTag noteTagSelector = new SelectNoteTag();
            ArrayList<Tag> tags = noteTagSelector.selectTagsByNote(id);
            Note note = new Note(id, title, text, tags, pin, date); 
               System.out.println(id + "," + title + "," + text + "," + pin + "," 
                				+ date);
            return note;
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }

        return null;
	}

	public ArrayList<Note> selectAllNotes() {
	
		ArrayList<Note> notes = new ArrayList<Note>();
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(QUERY_ALL);) {
            System.out.println(statement);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int id = rs.getInt("NOTEID");
            	String title = rs.getString("TITLE");
            	String text = rs.getString("TEXT");
            	boolean pin = rs.getBoolean("PIN");
            	Date date = rs.getDate("DATE");

            	SelectNoteTag noteTagSelector = new SelectNoteTag();
            	ArrayList<Tag> tags = noteTagSelector.selectTagsByNote(id);
            	Note note = new Note(id, title, text, tags, pin, date); 
            	notes.add(note);
            	System.out.println(id + "," + title + "," + text + "," + pin + "," 
            			+ date);
            }
            return notes;
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
 
        return null;
	}
}
