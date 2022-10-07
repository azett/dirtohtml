# DirToHTML
This neat little program scans a given directory and lists its contents to HTML files.

![screenshot.png](/screenshot.png?raw=true "DirToHTML screenshot")

## Usage
### Simple start
- Just double-click `DirToHTML.jar` to start DirToHMTL.
- Set options to your liking.
- Click "Select directory ...", select directory to scan.
- Select where the HTML files are to be written.
### Console start
    java -jar DirToHTML.jar [-TYPE SRC DEST]

Called without arguments, the graphical UI will open. Use the following arguments to supress the GUI:
- TYPE
  - `silent`: no program output at all
  - `console`: program output on standard console
- SRC: The source directory to process.
- DEST: The destination HTML file to store the result in.
