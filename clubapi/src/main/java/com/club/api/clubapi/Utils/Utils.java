package com.club.api.clubapi.Utils;

import java.sql.Timestamp;
import java.util.Calendar;

public abstract class Utils {

  public static Timestamp getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    return new Timestamp(calendar.getTimeInMillis());
  }
}
