package notebank.model;

import java.util.ArrayList;
import java.sql.Date;

public class Note {
	
	private int id;
	private String title;
	private String text;
	private ArrayList<Tag> tags;
	private boolean pin;
	private Date date; // can this be LocalDate?
	
	public Note() {}
	
	public Note(int id, String title, String text, ArrayList<Tag> tags, boolean pin, Date date) {
		this.setId(id);
		this.setTitle(title);
		this.setText(text);
		this.setTags(tags);
		this.setPin(pin);
		this.setDate(date);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
