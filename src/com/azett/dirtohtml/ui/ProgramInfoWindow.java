package com.azett.dirtohtml.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.config.Language;

// Programminfo-Dialog
public class ProgramInfoWindow extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Config und Sprache
    private Language          language         = Configuration
                                                       .getConfiguration()
                                                       .getLanguage();

    // Content-Panel
    public class MyPanel extends JPanel implements ActionListener {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public MyPanel() {
            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(gbl);

            // Bild
            // ImageIcon img = new
            // ImageIcon(this.getClass().getResource("../gfx/azett.gif"));
            ImageIcon img = new ImageIcon(this.getClass().getResource(
                    "/com/azett/dirtohtml/gfx/azett.gif"));
            JLabel lblImage = new JLabel(img);
            gbc = makegbc(0, 0, 1, 3);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 10, 0, 10);
            gbl.setConstraints(lblImage, gbc);
            add(lblImage);

            // Programmname
            JLabel lblProgram = new JLabel("<html><b>" + Defaults.PROGRAM_NAME
                    + " " + Defaults.PROGRAM_VERSION + "</b></html>");
            gbc = makegbc(1, 0, 1, 1);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 10, 0, 10);
            gbl.setConstraints(lblProgram, gbc);
            add(lblProgram);

            // Autor
            JLabel lblAuthor = new JLabel(Defaults.AUTHOR_NAME);
            gbc = makegbc(1, 1, 1, 1);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 10, 0, 10);
            gbl.setConstraints(lblAuthor, gbc);
            add(lblAuthor);

            // Verweis auf Website
            WeblinkJLabel lblWebsite = new WeblinkJLabel(
                    Defaults.AUTHOR_WEBSITE, Defaults.AUTHOR_WEBSITE_SHORT,
                    language.getString("msg.showWebsite",
                            new String[] { Defaults.AUTHOR_WEBSITE }));
            gbc = makegbc(1, 2, 1, 1);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 10, 0, 10);
            gbl.setConstraints(lblWebsite, gbc);
            add(lblWebsite);

            // ‹bersetzer
            JLabel lblTranslator = new JLabel(language.getString(
                    "lang.translatedby",
                    new String[] { language.getString("lang.translator") }));
            gbc = makegbc(0, 3, 2, 1);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 10, 10, 10);
            gbl.setConstraints(lblTranslator, gbc);
            add(lblTranslator);

            JButton btnClose = new JButton(language.getString("msg.close"));
            btnClose.addActionListener(this);
            gbc = makegbc(0, 4, 2, 1);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 0, 10, 0);
            gbl.setConstraints(btnClose, gbc);
            add(btnClose);

            this.setVisible(true);
        }

        // ActionEvent-Auswertung
        public void actionPerformed(ActionEvent e) {
            // Bei Klick: schlieﬂen
            dispose();
        }

        // GridBagConstraints erstellen
        private GridBagConstraints makegbc(int x, int y, int width, int height) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = y;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            return gbc;
        }
    }

    // Konstruktor
    public void showProgramInfoWindow() {
        MyPanel panel = new MyPanel();
        setContentPane(panel);
        this.setModal(true);
        this.setResizable(false);
        this.setTitle(language.getString("msg.programInfo"));
        this.pack();
        int myXpos = (Toolkit.getDefaultToolkit().getScreenSize().width - this
                .getWidth()) / 2; // Fenster horizontal...
        int myYpos = (Toolkit.getDefaultToolkit().getScreenSize().height - this
                .getHeight()) / 2; // ...und vertikal zentrieren
        this.setBounds(myXpos, myYpos, this.getWidth(), this.getHeight());
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
