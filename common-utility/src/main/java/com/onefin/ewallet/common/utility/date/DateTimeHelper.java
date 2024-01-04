package com.onefin.ewallet.common.utility.date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("dateHelper")
public class DateTimeHelper {

	public String currentDateString(String timeZone, String dateFormat) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime currentTime = new DateTime(DateTimeZone.UTC).withZone(zone);
		Date currentDate = currentTime.toLocalDateTime().toDate();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(currentDate);
	}

	public String previousDateString(String timeZone, String dateFormat) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime currentTime = new DateTime(DateTimeZone.UTC).withZone(zone);
		DateTime previousTime = currentTime.minusDays(1);
		Date previousDate = previousTime.toLocalDateTime().toDate();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(previousDate);
	}

	public Date previousDate(String date, String timeZone, String dateFormat) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateFormat)).withZone(zone);
		DateTime previousTime = dateTime.minusDays(1);
		return previousTime.toLocalDateTime().toDate();
	}

	public String previousDateString(String date, String timeZone, String dateFormat) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateFormat)).withZone(zone);
		DateTime previousTime = dateTime.minusDays(1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(previousTime.toLocalDateTime().toDate());
	}

	public String nextTDateString(String date, String timeZone, String dateFormat, int nextDate) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateFormat)).withZone(zone);
		DateTime nextTTime = dateTime.plusDays(nextDate);
		Date nextTDate = nextTTime.toLocalDateTime().toDate();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(nextTDate);
	}

	public Date currentDate(String timeZone) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime currentTime = new DateTime(DateTimeZone.UTC).withZone(zone);
		Date currentDate = currentTime.toLocalDateTime().toDate();
		return currentDate;
	}

	public DateTime currentDateTime(String timeZone) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime currentTime = new DateTime(DateTimeZone.UTC).withZone(zone);
		return currentTime;
	}

	public DateTime parseDate(String date, String timeZone, String dateFormat) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateFormat)).withZone(zone);
		return dateTime;
	}

	public Date parseDate2(String date, String timeZone, String dateFormat) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateFormat)).withZone(zone);
		Date currentDate = dateTime.toLocalDateTime().toDate();
		return currentDate;
	}

	public Date parseDate2(String date, String dateFormat) {
		DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern(dateFormat));
		Date currentDate = dateTime.toLocalDateTime().toDate();
		return currentDate;
	}

	public String parseDateString(DateTime date, String dateFormat) {
		Date currentDate = date.toLocalDateTime().toDate();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(currentDate);
	}

	public String parseDate2String(Date date, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}

	public String parseDate2DateString(String curDateFormat, String desDateFormat, String curDate, String timeZone) {
		DateTimeZone zone = DateTimeZone.forID(timeZone);
		DateTime dateTime = DateTime.parse(curDate, DateTimeFormat.forPattern(curDateFormat)).withZone(zone);
		Date currentDate = dateTime.toLocalDateTime().toDate();
		SimpleDateFormat formatter = new SimpleDateFormat(desDateFormat);
		return formatter.format(currentDate);
	}

	public String parseDate2DateString(String curDateFormat, String desDateFormat, String curDate) {
		DateTime dateTime = DateTime.parse(curDate, DateTimeFormat.forPattern(curDateFormat));
		Date currentDate = dateTime.toLocalDateTime().toDate();
		SimpleDateFormat formatter = new SimpleDateFormat(desDateFormat);
		return formatter.format(currentDate);
	}

	/* Find begin and end date of current month */
	public Pair<Date, Date> getDateRangeInMonth(Date date) {
		Date begining, end;

		{
			Calendar calendar = getCalendarForNow(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			setTimeToBeginningOfDay(calendar);
			begining = calendar.getTime();
		}

		{
			Calendar calendar = getCalendarForNow(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			setTimeToEndOfDay(calendar);
			end = calendar.getTime();
		}

		return Pair.of(begining, end);
	}

	public Date getBeginingOfDate(Date date) {
		Calendar calendar = getCalendarForNow(date);
		setTimeToBeginningOfDay(calendar);
		Date begining = calendar.getTime();

		return begining;
	}

	public Date getEndOfDate(Date date) {
		Calendar calendar = getCalendarForNow(date);
		setTimeToEndOfDay(calendar);
		Date end = calendar.getTime();

		return end;
	}

	private static Calendar getCalendarForNow(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

	/* Find begin and end date from end year and offset */
	public Pair<Date, Date> getDateRangeByYearOffset(int endYear, int offset) {
		int beginYear = endYear - offset;
		Date start, end;
		Calendar cal = Calendar.getInstance();
		{
			cal.set(Calendar.YEAR, beginYear);
			cal.set(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			start = cal.getTime();
		}

		{
			cal.set(Calendar.YEAR, endYear - 1);
			cal.set(Calendar.MONTH, 11); // 11 = december
			cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			end = cal.getTime();
		}

		return Pair.of(start, end);
	}

	public Date dateFromBigInt(BigInteger bigint) {
		long millis = bigint.longValue();
		return new Date(millis);
	}

	public List<Date> hoursDiff(Date fromDate, Date toDate) {
		Calendar calendarFromDate = getCalendarForNow(fromDate);
		Calendar calendarToDate = getCalendarForNow(toDate);
		List<Date> listDate = new ArrayList<>();
		int hourDiff = calendarToDate.get(Calendar.HOUR_OF_DAY)
				- calendarFromDate.get(Calendar.HOUR_OF_DAY)
				+ (calendarToDate.get(Calendar.DAY_OF_YEAR)
				- calendarFromDate.get(Calendar.DAY_OF_YEAR)) * 24;

		for (int i = 0; i <= hourDiff; i++) {
			DateTime fromDateTime = new DateTime(calendarFromDate.getTime());
			fromDateTime = fromDateTime.plusHours(i);
			Date exportDate = fromDateTime.toDate();
			listDate.add(exportDate);
		}
		return listDate;
	}

	public List<Date> dateDiff(Date fromDate, Date toDate) {
		Calendar calendarFromDate = getCalendarForNow(fromDate);
		Calendar calendarToDate = getCalendarForNow(toDate);
		List<Date> listDate = new ArrayList<>();
		int dateDiff = calendarToDate.get(Calendar.DAY_OF_YEAR)
				- calendarFromDate.get(Calendar.DAY_OF_YEAR) + 1;

		for (int i = 0; i < dateDiff; i++) {
			DateTime fromDateTime = new DateTime(calendarFromDate.getTime());
			fromDateTime = fromDateTime.plusDays(i);
			Date exportDate = fromDateTime.toDate();
			listDate.add(exportDate);
		}
		return listDate;
	}

	public int dateDiffInt(Date fromDate, Date toDate) {
		Calendar calendarFromDate = getCalendarForNow(fromDate);
		Calendar calendarToDate = getCalendarForNow(toDate);

		return calendarToDate.get(Calendar.DAY_OF_YEAR)
				- calendarFromDate.get(Calendar.DAY_OF_YEAR) + 1;
	}
}