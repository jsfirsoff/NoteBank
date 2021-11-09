package notebank.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// from title or text
// check each word separately unless in quotes?
//what about matching within a word?
public class SearchNotesByString implements SearchNotes {
	
	private String searchString;
	
	public SearchNotesByString(String searchString) {
		this.searchString = searchString.toLowerCase();
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {
		//String[] dividedStrings;
		ArrayList<String> dividedStrings = new ArrayList<String>();
		ArrayList<String> literalStrings = new ArrayList<String>();
		//ArrayList<Pair<Integer, Integer>> literalIndices = new ArrayList<Pair<Integer, Integer>>();
		
		Pattern p = Pattern.compile("(\'([^\']*)\')|(\"([^\"]*)\")");
		Matcher m = p.matcher(searchString);
		
		while (m.find()) {
			//literalIndices.
			String s = searchString.substring(m.start(), m.end());
			if (!s.isBlank()) literalStrings.add(s); //check for empties
			searchString = searchString.replace(s, "");
		}
		dividedStrings = new ArrayList<String>(Arrays.asList(searchString.split(" ")));
		
		ArrayList<Note> allNotes = noteFinder.getAllNotes();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		for (Note note : allNotes) {
		//	boolean found = false;
			String title = note.getTitle().toLowerCase();
			String text = note.getText().toLowerCase();
			
			if (findMatch(literalStrings, title) ||
					findMatch(dividedStrings, title) ||
					findMatch(literalStrings, text) ||
					findMatch(dividedStrings, text)) {
				notes.add(note);
			}
			
			/*
			 * for (String word : literalStrings) { if(title.indexOf(word) > -1) {
			 * notes.add(note); found = true; break; } } if (found) continue; for (String
			 * word : dividedStrings) { if (title.indexOf(word) > -1) { notes.add(note);
			 * found = true; break; } } if (found) continue; for (String word :
			 * literalStrings) { if(text.indexOf(word) > -1) { notes.add(note); found =
			 * true; break; } } if (found) continue; for (String word : dividedStrings) { if
			 * (title.indexOf(word) > -1) { notes.add(note); found = true; break; } }
			 */

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
