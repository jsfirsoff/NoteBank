package notebank.model;

import java.util.ArrayList;

public class NoteSort implements SortNotes {
	
	private SortNotes noteSort;
	
	public NoteSort(SortNotes noteSort) {
		this.noteSort = noteSort;
	}

	@Override
	public ArrayList<Note> sortNotes(ArrayList<Note> notes) {
		return noteSort.sortNotes(notes);
	}

}
