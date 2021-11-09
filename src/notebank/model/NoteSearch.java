package notebank.model;

import java.util.ArrayList;
// this is used by selectnotes
public class NoteSearch implements SearchNotes {
	
	private SearchNotes noteSearch;
	
	public NoteSearch(SearchNotes noteSearch) {
		this.noteSearch = noteSearch;
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {

		return noteSearch.getNotes(noteFinder);
	}

}
