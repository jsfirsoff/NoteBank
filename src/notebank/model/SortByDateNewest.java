package notebank.model;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByDateNewest implements SortNotes {
	
	public SortByDateNewest() {}

	@Override
	public ArrayList<Note> sortNotes(ArrayList<Note> notes) {

		notes.sort(new Comparator<Note>() {

			@Override
			public int compare(Note note1, Note note2) {
				return note1.getDate().compareTo(note2.getDate()) * -1; //reverse by switching pos/neg sign
			}	
		});
		
		return notes;
	}

}
