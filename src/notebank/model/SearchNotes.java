package notebank.model;

import java.util.ArrayList;
// this is used by selectnotes
// base decorator class
public class SearchNotes implements ISearchNotes {
	
	private ISearchNotes noteSearch;
	
	public SearchNotes(ISearchNotes noteSearch) {
		this.noteSearch = noteSearch;
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {

		return noteSearch.getNotes(noteFinder);
	}

}
