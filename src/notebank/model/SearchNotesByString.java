package notebank.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// from title or text
// check each word separately unless in quotes?
//what about matching within a word?

public class SearchNotesByString implements ISearchNotes {
	
	private String searchString;
	
	public SearchNotesByString(String searchString) {
		this.searchString = searchString.toLowerCase();
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {

		ArrayList<String> dividedStrings = new ArrayList<String>();
		ArrayList<String> literalStrings = new ArrayList<String>();
		

		//list search terms between quotations
		Pattern p = Pattern.compile("(\'([^\']*)\')|(\"([^\"]*)\")");
		Matcher m = p.matcher(searchString);
		
		while (m.find()) {
			String s = searchString.substring(m.start(), m.end());
			if (!s.isBlank()) literalStrings.add(s.substring(1, s.length()-2)); //check for empties and remove quotes
			searchString = searchString.replace(s, "");
		}
		
		//list separate each word in search term
		if (!searchString.isBlank()) dividedStrings = new ArrayList<String>(Arrays.asList(searchString.split(" ")));
		
		ArrayList<Note> allNotes = noteFinder.getAllNotes();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		for (Note note : allNotes) {
	
			String title = note.getTitle().toLowerCase();
			String text = note.getText().toLowerCase();
			//find matches in title or text from literal or divided lists
			if (findMatch(literalStrings, title) ||
					findMatch(dividedStrings, title) ||
					findMatch(literalStrings, text) ||
					findMatch(dividedStrings, text)) {
				notes.add(note);
			}
			
		}
		return notes;
	}
	
	private boolean findMatch(ArrayList<String> searchWords, String text) { //add return list?
		for (String word : searchWords) {
			if (text.indexOf(word) > -1) return true;
		}
		return false;
	}

}
