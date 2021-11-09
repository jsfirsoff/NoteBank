package notebank.database;

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
		// using try-with-resources to avoid closing resources (boiler plate code)

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
            String text = rs.getString("TEXT"); // convert clob to string? ... why is this clob different?
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
        // Step 4: try-with-resource statement will auto close the connection.
        return null;
	}
	// may have to implement multiple methods here to address various search types...return lists etc
	//decorator pattern for search methods?
	public ArrayList<Note> selectAllNotes() {
		// using try-with-resources to avoid closing resources (boiler plate code)
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
            	String text = rs.getString("TEXT"); // convert clob to string? ... why is this clob different?
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
        // Step 4: try-with-resource statement will auto close the connection.
        return null;
	}
}
