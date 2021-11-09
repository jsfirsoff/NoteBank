package test.notebank.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import notebank.model.Note;
import notebank.model.NoteSort;
import notebank.model.SortByDateNewest;

public class SortByDateNewestTest {
	
	static ArrayList<Note> notes = new ArrayList<Note>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		notes.add(new Note(1, "title", "text", null, false, Date.valueOf("2020-03-11")));
		notes.add(new Note(2, "title", "text", null, false, Date.valueOf("2020-11-11")));
		notes.add(new Note(3, "title", "text", null, false, Date.valueOf("2019-04-04")));
	}

	@Test
	public void test() {
		NoteSort sort = new NoteSort(new SortByDateNewest());
		
		ArrayList<Note> sortedNotes = sort.sortNotes(notes);
		
		assertTrue(sortedNotes.get(0).getDate().after(sortedNotes.get(1).getDate()));
		assertTrue(sortedNotes.get(1).getDate().after(sortedNotes.get(2).getDate()));
	}

}
