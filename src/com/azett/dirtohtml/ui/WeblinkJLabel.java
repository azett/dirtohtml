package com.azett.dirtohtml.ui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.azett.dirtohtml.config.Defaults;
import com.javaworld.markweakly.BrowserControl;

/**
 * @author azimmermann
 */
public class WeblinkJLabel
        extends JLabel {

    /**
     * @author azimmermann
     */
    public WeblinkJLabel(String url, String linktext, String tooltip) {
        this.setText("<html><a href=\"" + url + "\">" + linktext + "</a></html>");
        this.setToolTipText(tooltip);
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                BrowserControl.displayURL(Defaults.AUTHOR_WEBSITE);
            }

            public void mouseEntered(MouseEvent e) {
                if (e.getSource() instanceof JLabel)
                    ((JLabel) e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

        });
    }

}
