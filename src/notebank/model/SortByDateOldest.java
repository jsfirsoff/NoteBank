package notebank.model;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByDateOldest implements SortNotes {
	
	public SortByDateOldest() {}

	@Override
	public ArrayList<Note> sortNotes(ArrayList<Note> notes) {
		notes.sort(new Comparator<Note>() {

			@Override
			public int compare(Note note1, Note note2) {
				return note1.getDate().compareTo(note2.getDate()); 
			}	
		});
		
		return notes;
	}

}
