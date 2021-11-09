package notebank.model;

import java.util.ArrayList;
// should only return 1
public class SearchNotesById implements SearchNotes {
	
	private int id;
	
	public SearchNotesById(int id) {
		this.id = id;
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) { //may be bettr just to call method in selectnote class
		ArrayList<Note> notes = new ArrayList<Note>();
		for (Note note : noteFinder.getAllNotes()) {
			if (note.getId() == id) {
				notes.add(note);
			}
		}
		return notes;
	}

}