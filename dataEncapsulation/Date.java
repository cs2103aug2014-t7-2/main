	/**
	 * @author Kadayam Suresh Kaushik A0108297X
	 * Date Class (Instant) containing Constructor and all methods.
	 * @return format: Depends on which method is being used.
	 */
package dataEncapsulation;

import java.time.*;
import java.util.*;

import powerSearch.Searcher;
public class Date {
	private int day, month, year, hours, minutes, seconds;
	private long time;
	private Calendar cal = GregorianCalendar.getInstance();
	
	private static boolean dmFormat = true;
	
	public Date(int userday, int usermonth, int useryear) {
		if(dateValid(userday, usermonth, useryear)){
			this.setDay(userday);
			this.setMonth(usermonth);
			this.setYear(useryear);
			cal.set(useryear, usermonth, userday);
			this.setHours(0);
			this.setMinutes(0);
			this.setSeconds(0);
			this.setTime(cal.getTimeInMillis());
		}
	}

	public Date(int userday, int usermonth, int useryear, int hours, int minutes, int seconds) {
		if(dateValid(userday, usermonth, useryear)){
			this.setDay(userday);
			this.setMonth(usermonth);
			this.setYear(useryear);
			this.setHours(hours);
			this.setMinutes(minutes);
			this.setSeconds(seconds);
			cal.set(useryear, usermonth, userday, hours, minutes, seconds);
			this.setTime(cal.getTimeInMillis());
		}
	}
	
	public Date(){
		cal = Calendar.getInstance();
		this.day = cal.get(Calendar.DATE);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);getClass();
		this.setHours(0);
		this.setMinutes(0);
		this.setSeconds(0);
		cal.set(year, month, day);
		this.setTime(cal.getTimeInMillis());
	}

	public boolean dateValid(int userday, int usermonth, int useryear){
		boolean dateIsValid = true;
		try {
			LocalDate.of(useryear, usermonth, userday);
		} catch (DateTimeException e) {
			dateIsValid = false;
		}
		return dateIsValid;
	}

	public boolean isBefore(Date anotherdate){
		boolean answer = false;
		if(anotherdate instanceof Date && anotherdate.getDay()!=0){ //avoids the case whereby the enddate has not been stated by the user
			Calendar calcompare = anotherdate.getCal();
			if(cal.before(calcompare)){
				return true;
			}
		}
		return answer;
	}

	public boolean isEqual(Date anotherdate){
		if(anotherdate.getDay() == day && anotherdate.getMonth() == month && anotherdate.getYear() == year) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author yuiwei
	 * @param another
	 * @return
	 */
	public boolean isEquals(Date another) {
		if (this.day == another.day && this.month == another.month && this.year == another.year) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Calendar getCal(){
		return this.cal;
	}
	
	public static void changeFormatDm() {
		dmFormat = true;
	}
	
	public static void changeFormatMd() {
		dmFormat = false;
	}
	
	public static boolean isFormatDm() {
		return dmFormat;
	}

	public static Date determineDate(String dateString) {
		if (dateString == null) {
			return null;
		}

		String month, day, year;
		int mm, dd, yyyy;
		if (dateString.contains(",")) {
			int space = dateString.indexOf(' ');
			int comma = dateString.indexOf(',');
			month = dateString.substring(0, space);
			day = dateString.substring(space + 1, comma);
			year = dateString.substring(comma);
			year = cleanUpYear(year);
			mm = monthInteger(month);
		} else if (dateString.contains("/")) {
			int dayIndex = 0;
			int monthIndex = 1;
			if (!dmFormat) {
				dayIndex = 1;
				monthIndex = 0;
			}
			
			String dateStr[] = dateString.split("/", 3);
			day = dateStr[dayIndex];
			month = dateStr[monthIndex];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		} else if (dateString.contains("-")) {
			int yearIndex = 0;
			int dayIndex = 2;
			int monthIndex = 1;
			
			String dateStr[] = dateString.split("-", 3);
			day = dateStr[dayIndex];
			month = dateStr[monthIndex];
			year = dateStr[yearIndex];
			mm = Integer.parseInt(month);
		} else {
			String dateStr[] = dateString.split(" ", 3);
			day = dateStr[0];
			month = dateStr[1];
			year = dateStr[2];
			mm = Integer.parseInt(month);
		}
		dd = Integer.parseInt(day);
		yyyy = Integer.parseInt(year);

		Date userDate = new Date(dd, mm, yyyy);
		return userDate;
	}

	private static String cleanUpYear(String year) {
		String space = " ";
		String comma = ",";
		String emptyString = new String();

		if (year.contains(space)) {
			year = year.replace(space, emptyString);
		}

		if (year.contains(comma)) {
			year = year.replace(comma, emptyString);
		}

		return year;
	}

	public static int monthInteger(String month1) {
		int monthNum;
		switch (month1.toLowerCase()) {
		case "january" :		
			monthNum = 1; 			
			break;
		case "february":		
			monthNum = 2;				
			break;
		case "march":			
			monthNum = 3;				
			break;
		case "april":			
			monthNum = 4;				
			break;
		case "may":				
			monthNum = 5;				
			break;
		case "june":			
			monthNum = 6;				
			break;
		case "july":			
			monthNum = 7;				
			break;
		case "august":			
			monthNum = 8;				
			break;
		case "september":		
			monthNum = 9;				
			break;
		case "october":			
			monthNum = 10;				
			break;
		case "november":		
			monthNum = 11;				
			break;
		case "december":		
			monthNum = 12;				
			break;
		default: 				
			monthNum = 0;				
			break;
		}
		return monthNum;
	}

	public String toString(){
		String answer = new String();
		String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday", "Friday", "Saturday" };
		switch(this.getMonth()){
		case 1:
			answer = "January";
			break;
		case 2:
			answer = "February";
			break;
		case 3:
			answer = "March";
			break;
		case 4:
			answer = "April";
			break;
		case 5:
			answer = "May";
			break;
		case 6:
			answer = "June";
			break;
		case 7:
			answer = "July";
			break;
		case 8:
			answer = "August";
			break;
		case 9:
			answer = "September";
			break;
		case 10:
			answer = "October";
			break;
		case 11:
			answer = "November";
			break;
		case 12:
			answer = "December";
			break;
		}
		return strDays[cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) - 1] + " " + answer + " " + this.getDay() + ", " + this.getYear();
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
