package notebank.model;

import java.sql.Date;
import java.util.ArrayList;

public class SearchNotesByDate implements ISearchNotes {

	private Date date;
	
	public SearchNotesByDate(Date date) {
		this.date = date;
	}
	
	
	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) { 
		ArrayList<Note> notes = new ArrayList<Note>();
		for (Note note : noteFinder.getAllNotes()) {
			if (note.getDate().equals(date)) {
				notes.add(note);
			}
		}
		return notes;
	}

}
