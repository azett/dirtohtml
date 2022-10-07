package com.azett.dirtohtml.processing.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.config.Language;
import com.azett.dirtohtml.processing.model.AbstractModel;
import com.azett.dirtohtml.processing.model.DirToHTMLModel;
import com.azett.dirtohtml.ui.UiUtils;

/**
 * Die Swing-View für das MVC-Pattern
 * 
 * @author azimmermann
 * 
 */
public class SwingView extends AbstractView {

    private AbstractModel model;
    private ProgressFrame frmProgress;

    // Config und Sprache
    private Configuration config   = Configuration.getConfiguration();
    private Language      language = config.getLanguage();

    /**
     * @param model
     */
    public SwingView(AbstractModel model) {
        this.model = model;
        this.frmProgress = new ProgressFrame();
    }

    /**
     * Setzt den übergebenen Text ins Verzeichnis-Label.
     * 
     * @param text
     */
    public void setCurrentDirLabel(String text) {
        this.frmProgress.setCurrentDirLabel(text);
    }

    /**
     * Setzt den übergebenen Text ins Datei-Label.
     * 
     * @param text
     */
    public void setCurrentFileLabel(String text) {
        this.frmProgress.setCurrentFileLabel(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.azett.dirtohtml.processing.view.AbstractView#onCancel(java.beans.
     * PropertyChangeEvent )
     */
    public void onCancel(final PropertyChangeEvent evt) {
        ((DirToHTMLModel) this.model).cancelProcessing();
        this.frmProgress.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.azett.dirtohtml.processing.view.AbstractView#onError(java.beans.
     * PropertyChangeEvent )
     */
    public void onError(final PropertyChangeEvent evt) {
        UiUtils.showErrorDialog(frmProgress, language.getString("msg.cancel"),
                evt.getNewValue().toString());
        this.frmProgress.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.azett.dirtohtml.processing.view.AbstractView#onCurrentDir(java.beans.
     * PropertyChangeEvent)
     */
    public void onCurrentDir(final PropertyChangeEvent evt) {
        frmProgress.setCurrentDirLabel(evt.getNewValue().toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.azett.dirtohtml.processing.view.AbstractView#onCurrentFile(java.beans
     * . PropertyChangeEvent)
     */
    public void onCurrentFile(final PropertyChangeEvent evt) {
        frmProgress.setCurrentFileLabel(evt.getNewValue().toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.azett.dirtohtml.processing.view.AbstractView#onDone(java.beans.
     * PropertyChangeEvent )
     */
    public void onDone(final PropertyChangeEvent evt) {
        frmProgress.dispose();
    }

    /**
     * Fortschrittsfenster
     * 
     * @author azimmermann
     * 
     */
    class ProgressFrame extends JFrame implements ActionListener {
        // Config und Sprache
        private Configuration config   = Configuration.getConfiguration();
        private Language      language = config.getLanguage();

        private JLabel        lblcurrentdir;
        private JLabel        lblcurrentfile;
        private JButton       btnCancel;

        /**
         * Konstruktor
         */
        public ProgressFrame() {
            this.initialize();
            this.setVisible(true);
        }

        /**
         * Initilaisiert die UI.
         */
        private void initialize() {
            // Oberfläche mit Content-Panel aufbauen
            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            this.getContentPane().setLayout(gbl);

            this.lblcurrentdir = new JLabel("");
            this.lblcurrentdir.setPreferredSize(new Dimension(500, 25));
            gbc = makegbc(0, 0, 1, 1);
            gbl.setConstraints(lblcurrentdir, gbc);
            this.getContentPane().add(this.lblcurrentdir);

            this.lblcurrentfile = new JLabel("");
            this.lblcurrentfile.setPreferredSize(new Dimension(500, 25));
            gbc = makegbc(0, 1, 1, 1);
            gbl.setConstraints(lblcurrentfile, gbc);
            this.getContentPane().add(this.lblcurrentfile);

            this.btnCancel = new JButton(language.getString("msg.cancel"));
            btnCancel.addActionListener(this);
            gbc = makegbc(0, 2, 1, 1);
            gbl.setConstraints(btnCancel, gbc);
            this.getContentPane().add(btnCancel);

            this.getContentPane().setVisible(true);

            // sonstige Eigenschaften des Statusfensters
            this.setTitle(Defaults.PROGRAM_NAME + " - "
                    + language.getString("msg.readFiles"));
            this.setResizable(false);
            this.setIconImage(this.getToolkit().getImage(
                    (this.getClass())
                            .getResource("/com/azett/dirtohtml/gfx/icon.gif")));
            this.pack();
            // mittig auf den Bildschirm
            int myXpos = (Toolkit.getDefaultToolkit().getScreenSize().width - this
                    .getWidth()) / 2; // Fenster horizontal...
            int myYpos = (Toolkit.getDefaultToolkit().getScreenSize().height - this
                    .getHeight()) / 2; // ...und vertikal zentrieren
            this.setBounds(myXpos, myYpos, this.getWidth(), this.getHeight());
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }

        /**
         * Erstellt neue {@link GridBagConstraints}.
         * 
         * @author azimmermann
         * @param x
         * @param y
         * @param width
         * @param height
         * @return
         */
        private GridBagConstraints makegbc(int x, int y, int width, int height) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = y;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            gbc.insets = new Insets(2, 10, 2, 10);
            return gbc;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == this.btnCancel) {
                ((DirToHTMLModel) model).cancelProcessing();
            }
            this.dispose();
        }

        /**
         * Setzt den übergebenen Text ins Verzeichnis-Label.
         * 
         * @param text
         */
        public void setCurrentDirLabel(String text) {
            this.lblcurrentdir.setText(text);
        }

        /**
         * Setzt den übergebenen Text ins Datei-Label.
         * 
         * @param text
         */
        public void setCurrentFileLabel(String text) {
            this.lblcurrentfile.setText(text);
        }

    }
}
