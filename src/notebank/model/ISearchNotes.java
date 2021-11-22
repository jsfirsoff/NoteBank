package notebank.model;

import java.util.ArrayList;

public interface ISearchNotes {
	ArrayList<Note> getNotes(FindNotes noteFinder);
}
