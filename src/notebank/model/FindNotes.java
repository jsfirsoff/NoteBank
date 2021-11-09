package notebank.model;

import java.util.ArrayList;

import notebank.database.SelectNote;
import notebank.database.SelectNoteTag;
import notebank.database.SelectTag;

public class FindNotes {
	
	SelectNote noteSelector;
	SelectNoteTag noteTagSelector;
	SelectTag tagSelector;
	
	public FindNotes() {
		noteSelector = new SelectNote();
		noteTagSelector = new SelectNoteTag();
		tagSelector = new SelectTag();
	}
	
	public ArrayList<Note> getAllNotes(){
		return noteSelector.selectAllNotes();
	}
	
	public ArrayList<Tag> getAllTags(){
		return tagSelector.selectAllTags();
	}
	
	public ArrayList<Note> getNotesByTag(int tagId){
		return noteTagSelector.selectNotesByTag(tagId);
	}
	
	public ArrayList<Tag> getTagsByNote(int noteId){
		return noteTagSelector.selectTagsByNote(noteId);
	}

}
