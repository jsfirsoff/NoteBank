package notebank.model;

import java.sql.Date;
import java.util.ArrayList;

public class SearchNotesByDate implements SearchNotes {

	private Date date;
	
	public SearchNotesByDate(Date date) {
		this.date = date;
	}
	
	
	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {  //may be better to make method in selectnoteclass
		ArrayList<Note> notes = new ArrayList<Note>();
		for (Note note : noteFinder.getAllNotes()) {
			if (note.getDate().equals(date)) {
				notes.add(note);
			}
		}
		return notes;
	}

}
