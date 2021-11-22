package notebank.model;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByPriority implements ISortNotes {
	
	public SortByPriority() {}

	@Override
	public ArrayList<Note> sortNotes(ArrayList<Note> notes) {
		notes.sort(new Comparator<Note>() {

			@Override
			public int compare(Note note1, Note note2) {
				return Boolean.compare(note1.isPin(), note2.isPin()) * -1;			
			}	
		});
		
		return notes;
	}

}
