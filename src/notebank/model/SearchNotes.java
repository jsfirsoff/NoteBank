package notebank.model;

import java.util.ArrayList;

public interface SearchNotes {
	ArrayList<Note> getNotes(FindNotes noteFinder);
}
