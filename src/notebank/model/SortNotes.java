package notebank.model;

import java.util.ArrayList;
// base decorator class

public class SortNotes implements ISortNotes {
	
	private ISortNotes noteSort;
	
	public SortNotes(ISortNotes noteSort) {
		this.noteSort = noteSort;
	}

	@Override
	public ArrayList<Note> sortNotes(ArrayList<Note> notes) {
		return noteSort.sortNotes(notes);
	}

}
