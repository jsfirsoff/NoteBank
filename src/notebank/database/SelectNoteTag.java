package notebank.database;
/**
 * Adapted from @author Ramesh Fadatare example
 * https://www.sourcecodeexamples.net/2019/11/jdbc-select-query-example.html
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import notebank.model.Note;
import notebank.model.Tag;

public class SelectNoteTag {
	private static final String NOTE_QUERY = "SELECT ntid,noteid,tagid FROM notetags WHERE noteid =?";
	private static final String TAG_QUERY = "SELECT ntid,noteid,tagid FROM notetags WHERE tagid =?";
	private SelectTag tagSelector = new SelectTag();
	private SelectNote noteSelector = new SelectNote();

	public ArrayList<Tag> selectTagsByNote(int noteId) {
		
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(NOTE_QUERY);) {
            statement.setInt(1, noteId); // set index of parameter and parameter type
            System.out.println(statement);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            ArrayList<Tag> tags = new ArrayList<Tag>();
            while (rs.next()) {
            	int tagid = rs.getInt("TAGID"); 
            	
            	Tag tag = tagSelector.selectTag(tagid); //may have to change this to include list of notes

            	tags.add(tag); 
            }
            return tags;
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        
        return null;
	}

	public ArrayList<Note> selectNotesByTag(int tagId) {

        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(TAG_QUERY);) {
            statement.setInt(1, tagId); // set index of parameter and parameter type
            System.out.println(statement);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            ArrayList<Note> notes = new ArrayList<Note>();
            while (rs.next()) {
            	int noteid = rs.getInt("NOTEID"); 
            	
            	Note note = noteSelector.selectNote(noteid);
            	notes.add(note); // don't need to fill tags list here? - > actually yes we do  ?    	
            }
            return notes;
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        
        return null;
	}
}
