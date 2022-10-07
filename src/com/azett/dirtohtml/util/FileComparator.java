package com.azett.dirtohtml.util;

import java.io.File;
import java.text.Collator;
import java.util.Comparator;

/**
 * {@link Comparator} zum Vergleichen von {@link File}s.
 * 
 * @author http://www.janek.org/
 */
public class FileComparator implements Comparator {

    private Collator c = Collator.getInstance();

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(T, T)
     */
    public int compare(Object o1, Object o2) {
        if (o1 == o2)
            return 0;

        File f1 = (File) o1;
        File f2 = (File) o2;

        if (f1.isDirectory() && f2.isFile())
            return -1;
        if (f1.isFile() && f2.isDirectory())
            return 1;

        return c.compare(f1.getName(), f2.getName());
    }
}