package notebank.model;

import java.util.ArrayList;
// include all tags or any tags
// use selectnotetag class instead?
public class SearchNotesByAnyTags implements SearchNotes {
	
	private ArrayList<String> tags;
	
	public SearchNotesByAnyTags(ArrayList<String> tags) {
		this.tags = tags;
		tags.replaceAll(String::toLowerCase);
	}

	@Override
	public ArrayList<Note> getNotes(FindNotes noteFinder) {
		ArrayList<Note> notes = new ArrayList<Note>();

		ArrayList<Tag> allTags = noteFinder.getAllTags();
		// include any tags
		for (Tag tag : allTags) {
			if (tags.contains(tag.getName().toLowerCase())) {
				notes.addAll(noteFinder.getNotesByTag(tag.getId()));
			}
		}

		return notes;
	}
}
