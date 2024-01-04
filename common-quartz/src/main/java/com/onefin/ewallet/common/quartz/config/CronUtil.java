package com.onefin.ewallet.common.quartz.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;

public class CronUtil {

  private final Date date;
  private final Calendar calendar;
  private final String Second = "0";
  private final String daysOfWeek = "?";

  private String minutes;
  private String hours;
  private String daysOfMonth;
  private String months;
  private String years;

  private DateTime dateTime;

  public CronUtil(Date date) {
    this.date = date;
    calendar = Calendar.getInstance();
    this.generateCronExpression();
  }

  private void generateCronExpression() {
    calendar.setTime(date);

    String hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
    this.hours = hours;

    String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
    this.minutes = minutes;

    String days = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    this.daysOfMonth = days;

    String months = new SimpleDateFormat("MM").format(calendar.getTime());

    this.months = months;

    String years = String.valueOf(calendar.get(Calendar.YEAR));
    this.years = years;
  }

  public String buildCronExpression() throws ParseException {
    String expression =
      this.Second +
      " " +
      this.minutes +
      " " +
      this.hours +
      " " +
      this.daysOfMonth +
      " " +
      this.months +
      " " +
      this.daysOfWeek +
      " " +
      this.years;

    return expression;
  }
}
