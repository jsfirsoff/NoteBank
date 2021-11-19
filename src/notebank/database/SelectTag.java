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

import notebank.model.Tag;

public class SelectTag {

	private static final String QUERY = "SELECT tagid,name FROM tags WHERE tagid =?";
	private static final String QUERY_ALL = "SELECT * FROM tags";
	
	public Tag selectTag(int targetId) {

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
            int id = rs.getInt("TAGID");
            String name = rs.getString("NAME");
 
            Tag tag = new Tag(id, name);  //until xover implementation .. or keep null? and let nts handle it?
               System.out.println(id + "," + name);
            return tag;
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
      
        return null;
	}

	public ArrayList<Tag> selectAllTags() {
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement statement = connection.prepareStatement(QUERY_ALL);) {
            System.out.println(statement);
            // Step 3: Execute the query or update query
            ResultSet rs = statement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	int id = rs.getInt("TAGID");
            	String name = rs.getString("NAME");

            	Tag tag = new Tag(id, name);  //until xover implementation .. or keep null? and let nts handle it?
            	System.out.println(id + "," + name);
            	tags.add(tag);
            }
            return tags;
            
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
       
        return null;
	}
}
