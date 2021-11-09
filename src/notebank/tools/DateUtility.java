package notebank.tools;

import java.time.LocalDate;
import java.sql.Date;
// may need to adjust what this class does
public class DateUtility {
	
	public DateUtility() {}
	
	public static Date getSQLDate() {
		
		LocalDate date = LocalDate.now();
		
		return Date.valueOf(date);
	}
	
	public static LocalDate getLocalDate(String date) {
		
		return LocalDate.parse(date);
	}
}
