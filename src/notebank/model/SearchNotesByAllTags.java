package notebank.model;

import java.util.ArrayList;

public class SearchNotesByAllTags implements SearchNotes {
	
	private ArrayList<String> tags;
	
	public SearchNotesByAllTags(ArrayList<String> tags) {
		this.tags = tags;
		tags.replaceAll(String::toLowerCase);
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {
		ArrayList<Note> notes = new ArrayList<Note>();
		ArrayList<Tag> noteTags = new ArrayList<Tag>();
		ArrayList<Note> allNotes = noteFinder.getAllNotes();

		// include all tags
		for (Note note : allNotes) {
			ArrayList<String> tagNames = new ArrayList<String>();
			noteTags = note.getTags();
			noteTags.forEach((tag) -> tagNames.add(tag.getName().toLowerCase()));
			if (tagNames.containsAll(tags)) {
				notes.add(note);
			}
		}

		return notes;
	}

}
