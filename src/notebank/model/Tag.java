package notebank.model;

public class Tag {

	private int id;
	private String name;
	//private ArrayList<Note> notes;
	
	public Tag() {}
	
	public Tag(int id, String name) { //, ArrayList<Note> notes) {
		this.setId(id);
		this.setName(name);
	//	this.setNotes(notes);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public ArrayList<Note> getNotes() { return notes; }
	 * 
	 * public void setNotes(ArrayList<Note> notes) { this.notes = notes; }
	 */
}
