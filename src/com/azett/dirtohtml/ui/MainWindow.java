package com.azett.dirtohtml.ui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.config.Language;
import com.azett.dirtohtml.processing.controller.DirToHTMLController;
import com.azett.dirtohtml.util.FileUtils;
import com.azett.dirtohtml.util.StdFileFilter;

/**
 * Programmfenster
 * 
 * @author azimmermann
 */
public class MainWindow extends JFrame {

    // Programminfos
    private ProgramInfoWindow programinfowindow = new ProgramInfoWindow();

    // Config und Sprache
    private Configuration     config            = Configuration
                                                        .getConfiguration();
    private Language          language          = config.getLanguage();

    //
    /**
     * Konstruktor
     * 
     * @author azimmermann
     */
    public MainWindow() {
        // System-UI holen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        // Oberfläche mit Content-Panel aufbauen
        JPanel contentPanel = new ContentPanel();
        this.setContentPane(contentPanel);
        this.pack();
        // Fenstergröße und -Position berechnen
        int myXpos = (Toolkit.getDefaultToolkit().getScreenSize().width - this
                .getWidth()) / 2; // Fenster horizontal...
        int myYpos = (Toolkit.getDefaultToolkit().getScreenSize().height - this
                .getHeight()) / 2; // ...und vertikal zentrieren
        this.setBounds(myXpos, myYpos, this.getWidth(), this.getHeight());
        // sonstige Eigenschaften des Programmfensters
        this.setTitle(Defaults.PROGRAM_NAME);
        this.setResizable(false);
        // URL imageurl = (this.getClass()).getResource("../gfx/icon.gif"); //
        // das Icon auch innerhalb...
        URL imageurl = (this.getClass())
                .getResource("/com/azett/dirtohtml/gfx/icon.gif"); // das Icon
                                                                   // auch
                                                                   // innerhalb...
        Image icon = this.getToolkit().getImage(imageurl); // ...der JAR-Datei
                                                           // finden
        this.setIconImage(icon);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Content-Panel
     * 
     * @author azimmermann
     */
    private class ContentPanel extends JPanel implements ActionListener {

        JCheckBox         chkMultiDocument;
        JCheckBox         chkHidden;
        JCheckBox         chkFullPath;
        JCheckBox         chkTooltip;
        JCheckBox         chkInfotable;
        JCheckBox         chkSize;
        JCheckBox         chkLastChange;
        JCheckBox         chkBitrate;
        JCheckBox         chkLinks;
        JCheckBox         chkLinksAsTargetBlank;
        JLabel            lblGeneral;
        JLabel            lblFileInfos;
        JLabel            lblFileLinks;
        JButton           btnStart;
        JButton           btnInfo;

        private final int TYPE_STANDARD     = 0;
        private final int TYPE_LABEL        = 1;
        private final int TYPE_CHECKBOX     = 2;
        private final int TYPE_OPTIONSPANEL = 3;

        /**
         * Konstruktor
         * 
         * @author azimmermann
         */
        public ContentPanel() {
            GridBagLayout gbl = new GridBagLayout();
            this.setLayout(gbl);

            // Optionen-Panel
            JPanel pnlOptions = new JPanel();
            pnlOptions.setLayout(gbl);
            pnlOptions.setBorder(new TitledBorder(language
                    .getString("msg.options")));

            lblGeneral = new JLabel(language.getString("msg.general"));
            gbl.setConstraints(lblGeneral, makegbc(TYPE_LABEL, 0, 0, 1, 1));
            pnlOptions.add(lblGeneral);

            chkMultiDocument = new JCheckBox(
                    language.getString("msg.createMultiDocuments"));
            gbl.setConstraints(chkMultiDocument,
                    makegbc(TYPE_CHECKBOX, 0, 1, 1, 1));
            chkMultiDocument.setToolTipText(language
                    .getString("msg.infoChkMultiDocument"));
            pnlOptions.add(chkMultiDocument);

            chkHidden = new JCheckBox(language.getString("msg.showHidden"));
            gbl.setConstraints(chkHidden, makegbc(TYPE_CHECKBOX, 0, 2, 1, 1));
            chkHidden.setToolTipText(language.getString("msg.infoChkHidden"));
            pnlOptions.add(chkHidden);

            chkFullPath = new JCheckBox(language.getString("msg.showFullPath"));
            gbl.setConstraints(chkFullPath, makegbc(TYPE_CHECKBOX, 0, 3, 1, 1));
            chkFullPath.setToolTipText(language
                    .getString("msg.infoChkFullpath"));
            pnlOptions.add(chkFullPath);

            chkTooltip = new JCheckBox(language.getString("msg.showTooltip"));
            gbl.setConstraints(chkTooltip, makegbc(TYPE_CHECKBOX, 0, 4, 1, 1));
            chkTooltip.setToolTipText(language.getString("msg.infoChkTooltip"));
            pnlOptions.add(chkTooltip);

            chkInfotable = new JCheckBox(
                    language.getString("msg.showInfotable"));
            gbl.setConstraints(chkInfotable, makegbc(TYPE_CHECKBOX, 0, 5, 1, 1));
            chkInfotable.setToolTipText(language
                    .getString("msg.infoChkInfotable"));
            pnlOptions.add(chkInfotable);

            lblFileInfos = new JLabel(language.getString("msg.fileInfos"));
            gbl.setConstraints(lblFileInfos, makegbc(TYPE_LABEL, 1, 0, 1, 1));
            pnlOptions.add(lblFileInfos);

            chkSize = new JCheckBox(language.getString("msg.showSize"));
            gbl.setConstraints(chkSize, makegbc(TYPE_CHECKBOX, 1, 1, 1, 1));
            chkSize.setToolTipText(language.getString("msg.infoChkSize"));
            pnlOptions.add(chkSize);

            chkLastChange = new JCheckBox(
                    language.getString("msg.showLastchange"));
            gbl.setConstraints(chkLastChange,
                    makegbc(TYPE_CHECKBOX, 1, 2, 1, 1));
            chkLastChange.setToolTipText(language
                    .getString("msg.infoChkLastchange"));
            pnlOptions.add(chkLastChange);

            chkBitrate = new JCheckBox(language.getString("msg.showBitrate"));
            gbl.setConstraints(chkBitrate, makegbc(TYPE_CHECKBOX, 1, 3, 1, 1));
            chkBitrate.setToolTipText(language.getString("msg.infoChkBitrate"));
            pnlOptions.add(chkBitrate);

            lblFileLinks = new JLabel(language.getString("msg.fileLinks"));
            gbl.setConstraints(lblFileLinks, makegbc(TYPE_LABEL, 1, 4, 1, 1));
            pnlOptions.add(lblFileLinks);

            chkLinks = new JCheckBox(language.getString("msg.createLinks"));
            gbl.setConstraints(chkLinks, makegbc(TYPE_CHECKBOX, 1, 5, 1, 1));
            chkLinks.addActionListener(this);
            chkLinks.setToolTipText(language.getString("msg.infoChkLinks"));
            pnlOptions.add(chkLinks);

            chkLinksAsTargetBlank = new JCheckBox(
                    language.getString("msg.createTargetBlank"));
            gbl.setConstraints(chkLinksAsTargetBlank,
                    makegbc(TYPE_CHECKBOX, 1, 6, 1, 1));
            chkLinksAsTargetBlank.setToolTipText(language
                    .getString("msg.infoChkLinksAsTargetBlank"));
            pnlOptions.add(chkLinksAsTargetBlank);

            gbl.setConstraints(pnlOptions,
                    makegbc(TYPE_OPTIONSPANEL, 0, 0, 2, 1));
            add(pnlOptions);

            // Buttons-Panel
            JPanel pnlButtons = new JPanel();
            pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            btnStart = new JButton(language.getString("msg.selectDirectory"));
            btnStart.addActionListener(this);
            btnStart.setToolTipText(language.getString("msg.infoBtnStart"));
            pnlButtons.add(btnStart);

            btnInfo = new JButton("?");
            btnInfo.addActionListener(this);
            btnInfo.setToolTipText(language.getString("msg.infoBtnInfo"));
            pnlButtons.add(btnInfo);

            gbl.setConstraints(pnlButtons, makegbc(TYPE_STANDARD, 0, 1, 2, 1));
            add(pnlButtons);

            // Gespeicherte Programmeinstellungen laden
            setOptionsInGui();

            // setSize(420,335);
            setVisible(true);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        public void actionPerformed(ActionEvent e) {
            Object src = new Object();
            src = e.getSource();
            // Checkbox "Dateien verlinken"
            if (src == this.chkLinks) {
                // Checkbox nicht gecheckt?
                if (!chkLinks.isSelected()) {
                    // Dann "Links in neuem Fenster öffnen" disablen
                    chkLinksAsTargetBlank.setSelected(false);
                    chkLinksAsTargetBlank.setEnabled(false);
                } else {
                    // ...sonst enablen
                    chkLinksAsTargetBlank.setEnabled(true);
                }
            }
            // Button "Verzeichnis einlesen..."
            else if (src == this.btnStart) {
                // Einstellungen speichern
                saveSettingsToFile();
                // Verzeichnis wählen
                File srcdir = getSourceDir();
                if (srcdir != null) {
                    // Zieldatei wählen
                    File destfile = getDestinationFile(srcdir);
                    if (destfile != null) {
                        // ...und los
                        // Processing processing = new Processing(srcdir,
                        // destfile);
                        // Thread t = new Thread(processing);
                        // t.start();
                        DirToHTMLController processing = new DirToHTMLController(
                                srcdir, destfile);
                        Thread t = new Thread(processing);
                        t.start();
                    }
                }
            }
            // Button "?"
            else if (src == this.btnInfo) {
                programinfowindow.showProgramInfoWindow();
            }
        }

        /**
         * Zeigt den Auswahldialog für das einzulesende Verzeichnis und gibt
         * selbiges zurück.
         * 
         * @author azimmermann
         * @return das gewählte Verzeichnis
         */
        private File getSourceDir() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(language
                    .getString("msg.selectDirectoryToRead"));
            return (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) ? chooser
                    .getSelectedFile() : null;
        }

        /**
         * Zeigt den Auswahldialog für die zu speichernde Datei gibt selbige
         * zurück.
         * 
         * @author azimmermann
         * @param sourceDir
         *            das einzulesende Verzeichnis
         * @return die gewählte Datei
         */
        private File getDestinationFile(File sourceDir) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogTitle(language.getString("msg.saveHtmlAs"));
            String[] filters = { "htm", "html" };
            chooser.addChoosableFileFilter(new StdFileFilter(filters, language
                    .getString("msg.htmlFiles")));

            chooser.setSelectedFile(new File(Defaults.PROGRAM_NAME + "_" //
                    + FileUtils.cleanFileName(sourceDir.getName()) //
                    + ".html"));
            // Gültige Datei im Chooser selektiert
            if ((chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                    // Datei noch nicht vorhanden ODER überschreiben bestätigt
                    && ((!chooser.getSelectedFile().exists()) || (JOptionPane
                            .showConfirmDialog(this, language.getString(
                                    "msg.overWriteFile", new String[] { chooser
                                            .getSelectedFile().getName() }),
                                    language.getString("msg.confirmation"),
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)))
                return chooser.getSelectedFile();
            else
                return null;
        }

        /**
         * Setzt die Einstellungen im GUI gemäß der gespeicherten Konfiguration.
         * 
         * @author azimmermann
         */
        public void setOptionsInGui() {
            // Optionen setzen
            chkMultiDocument.setSelected(config
                    .getBoolean(Configuration.CREATE_MULTI_DOCUMENT));
            chkHidden.setSelected(config
                    .getBoolean(Configuration.DISPLAY_HIDDEN));
            chkFullPath.setSelected(config
                    .getBoolean(Configuration.DISPLAY_FULL_PATH));
            chkTooltip.setSelected(config
                    .getBoolean(Configuration.DISPLAY_TOOLTIP));
            chkInfotable.setSelected(config
                    .getBoolean(Configuration.DISPLAY_PROGRAM_INFO));
            chkSize.setSelected(config
                    .getBoolean(Configuration.DISPLAY_FILE_SIZE));
            chkLastChange.setSelected(config
                    .getBoolean(Configuration.DISPLAY_LAST_CHANGE));
            chkBitrate.setSelected(config
                    .getBoolean(Configuration.DISPLAY_MP3_BITRATE));
            chkLinks.setSelected(config.getBoolean(Configuration.CREATE_LINKS));
            chkLinksAsTargetBlank.setSelected(config
                    .getBoolean(Configuration.CREATE_LINKS_AS_TARGET_BLANK));
            if (!this.chkLinks.isSelected()) {
                this.chkLinksAsTargetBlank.setSelected(false);
                this.chkLinksAsTargetBlank.setEnabled(false);
            }
        }

        /**
         * Speichert die aktuell im GUI eingestellten Optionen in die
         * Konfigurationsdatei.
         * 
         * @author azimmermann
         */
        public void saveSettingsToFile() {
            config.setBoolean(Configuration.CREATE_MULTI_DOCUMENT,
                    chkMultiDocument.isSelected());
            config.setBoolean(Configuration.DISPLAY_HIDDEN,
                    chkHidden.isSelected());
            config.setBoolean(Configuration.DISPLAY_FULL_PATH,
                    chkFullPath.isSelected());
            config.setBoolean(Configuration.DISPLAY_TOOLTIP,
                    chkTooltip.isSelected());
            config.setBoolean(Configuration.DISPLAY_PROGRAM_INFO,
                    chkInfotable.isSelected());
            config.setBoolean(Configuration.DISPLAY_FILE_SIZE,
                    chkSize.isSelected());
            config.setBoolean(Configuration.DISPLAY_LAST_CHANGE,
                    chkLastChange.isSelected());
            config.setBoolean(Configuration.DISPLAY_MP3_BITRATE,
                    chkBitrate.isSelected());
            config.setBoolean(Configuration.CREATE_LINKS, chkLinks.isSelected());
            config.setBoolean(Configuration.CREATE_LINKS_AS_TARGET_BLANK,
                    chkLinksAsTargetBlank.isSelected());
            config.saveToFile();
        }

        /**
         * Erstellt neue {@link GridBagConstraints} abhängig vom übergebenen
         * Element-Typ.
         * 
         * @author azimmermann
         * @param type
         *            Der Typ
         * @param x
         *            X-Position
         * @param y
         *            Y-Position
         * @param width
         *            Breite
         * @param height
         *            Höhe
         * @return die fertigen {@link GridBagConstraints}
         */
        private GridBagConstraints makegbc(int type, int x, int y, int width,
                int height) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = x;
            gbc.gridy = y;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            // Labels
            if (type == TYPE_LABEL) {
                gbc.insets = new Insets(7, 2, 2, 2);
                gbc.anchor = GridBagConstraints.WEST;
            }
            // Checkboxes
            else if (type == TYPE_CHECKBOX) {
                gbc.insets = new Insets(0, 5, 0, 2);
                gbc.anchor = GridBagConstraints.WEST;
            }
            // Options-Panel
            else if (type == TYPE_OPTIONSPANEL) {
                gbc.insets = new Insets(10, 2, 10, 2);
            }
            // Standard
            else {
                gbc.insets = new Insets(0, 2, 0, 2);
            }
            return gbc;
        }
    }
}
