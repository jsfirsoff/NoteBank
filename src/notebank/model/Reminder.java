package notebank.model;

import java.sql.Date;
import java.sql.Time;

public class Reminder {

	private int id;
	private Note note;
	private Date date;
	private Time time;
	private boolean sound; 
	
	public Reminder () {}
	
	public Reminder (int id, Note note, Date date, Time time, boolean sound) {
		this.setId(id);
		this.setNote(note);
		this.setDate(date);
		this.setTime(time);
		this.setSound(sound);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}
}
