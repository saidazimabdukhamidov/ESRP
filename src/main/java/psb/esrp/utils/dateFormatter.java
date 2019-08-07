package psb.esrp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dateFormatter {
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private LocalDateTime localDateTime;
    private DateTimeFormatter formatter;

    public String toDateFormat(String date){
      localDateTime = LocalDateTime.parse(date);
      formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      String formatDateTime = localDateTime.format(formatter);
      return formatDateTime;
    }
  }
