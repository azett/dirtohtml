package com.azett.dirtohtml.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.azett.dirtohtml.config.Defaults;

/**
 * Hilfsfunktionen
 * 
 * @author azimmermann
 */
public class DirToHTMLUtils {

    /**
     * Gibt das CSS-Template als String zurück
     * 
     * @author azimmermann
     * @return
     */
    public static String getCssTempate() {
        return FileUtils.getFileContent(Defaults.CSS_TEMPLATE_FILE);
    }

    /**
     * Gibt das HTML-Template als String zurück
     * 
     * @author azimmermann
     * @param title
     * @param css
     * @param js
     * @param heading
     * @param listing
     * @param programInfo
     * @return
     */
    public static String getHtmlTemplate(String title, String css, String js, String heading, String listing, String programInfo) {
        String template = FileUtils.getFileContent(Defaults.HTML_TEMPLATE_FILE);
        template = template.replace("{TITLE}", title);
        template = template.replace("{CSS}", css);
        template = template.replace("{JS}", js);
        template = template.replace("{HEADING}", heading);
        template = template.replace("{LISTING}", listing);
        template = template.replace("{PROGRAMINFO}", programInfo);
        return template;
    }

    /**
     * Gibt das JS-Template als String zurück
     * 
     * @author azimmermann
     * @return
     * @throws IOException
     */
    public static String getJsTemplate() {
        return FileUtils.getFileContent(Defaults.JS_TEMPLATE_FILE);
    }

    /**
     * Gibt das formatierte Datum als String zurück.
     * 
     * @author azimmermann
     * @param timestamp
     *            zu formatierender UNIX-Timestamp
     * @param timeformat
     *            Das Zeitformat (siehe http://download.oracle.com/javase/1.4.2/docs /api/java/text/SimpleDateFormat.html )
     * @return das formatierte Datum
     */
    public static String formatTime(long timestamp, String timeformat) {
        return new SimpleDateFormat(timeformat).format(new Date(timestamp));
    }

    /**
     * Gibt die übergebenen Millisekunden umgerechtnet in Sekunden zurück.
     * 
     * @author azimmermann
     * @param millis
     *            der Wert in Millisekunden
     * @return der Wert in Sekunden
     */
    public static String millisecondToSecond(long millis) {
        BigDecimal seconds = new BigDecimal(String.valueOf(millis));
        seconds = seconds.setScale(2);
        seconds = seconds.divide(new BigDecimal("1000"), BigDecimal.ROUND_UP);
        return String.valueOf(seconds) + "s";
    }

    /**
     * Gibt die übergebene Byte-Zahl als formatierten String zurück (B/KB/MB/GB - gewählt wird die passende Einheit). Der Wert wird
     * dabei auf zwei Stellen nach dem Komma gerundet.
     * 
     * @author azimmermann
     * @param byteValue
     *            der Byte-Wert
     * @return der formatierte String
     */
    public static String byteToString(long byteValue) {
        BigDecimal value = new BigDecimal(String.valueOf(byteValue));
        value = value.setScale(2);
        if (byteValue < 1024)
            return String.valueOf(byteValue) + " B";
        else if (byteValue < 1048576) {
            value = value.divide(new BigDecimal("1024"), BigDecimal.ROUND_UP);
            return String.valueOf(value) + " KB";
        } else if (byteValue < 1073741824) {
            value = value.divide(new BigDecimal("1048576"), BigDecimal.ROUND_UP);
            return String.valueOf(value) + " MB";
        } else {
            value = value.divide(new BigDecimal("1073741824"), BigDecimal.ROUND_UP);
            return String.valueOf(value) + " GB";
        }
    }
}
