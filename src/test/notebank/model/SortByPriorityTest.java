package test.notebank.model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import notebank.model.Note;
import notebank.model.NoteSort;
import notebank.model.SortByPriority;

public class SortByPriorityTest {

	static ArrayList<Note> notes = new ArrayList<Note>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		notes.add(new Note(1, "title", "text", null, false, Date.valueOf("2020-03-11")));
		notes.add(new Note(2, "title", "text", null, true, Date.valueOf("2020-11-11")));
		notes.add(new Note(3, "title", "text", null, false, Date.valueOf("2019-04-04")));
		notes.add(new Note(2, "title", "text", null, true, Date.valueOf("2021-01-01")));
	}

	@Test
	public void test() {
		NoteSort sort = new NoteSort(new SortByPriority());
		
		ArrayList<Note> sortedNotes = sort.sortNotes(notes);
		
		assertTrue(sortedNotes.get(0).isPin());
		assertTrue(sortedNotes.get(1).isPin());
		assertFalse(sortedNotes.get(2).isPin());
		assertFalse(sortedNotes.get(3).isPin());
	}

}
