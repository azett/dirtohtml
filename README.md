[![Releases](https://img.shields.io/github/release/azett/dirtohtml.svg?label=Latest%20release&style=plastic)](https://github.com/azett/dirtohtml/releases "See all releases")
[![License](https://img.shields.io/github/license/azett/dirtohtml.svg?style=plastic)](./LICENSE.md "License")
[![Open issues](https://img.shields.io/github/issues-raw/azett/dirtohtml?style=plastic)](https://github.com/azett/dirtohtml/issues "See open issues")
[![Last commit](https://img.shields.io/github/last-commit/azett/dirtohtml?style=plastic)](https://github.com/azett/dirtohtml/commits/ "Last commit")

# DirToHTML
This neat little program scans a given directory and lists its contents to HTML files. This might come handy if you'd like to list your movie or music collection, for example.

DirToHTML will happily run under any operating system as long as a [Java Runtime Environment](https://www.java.com/de/download/manual.jsp) (JRE) is present.

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

## Options
Use the following options to fit the HTML output to your needs. 

Be aware that each option adds more content to the HTML output, thus increasing the size of the HTML files and the processing duration. Rule of thumb: Activating all options multiplies duration and output size by factor 10.
- __Create one HTML file for each directory__<br>If this option is active, a separate HTML file is created for each subdirectory scanned. A click on a subdirectory opens its HTML output.<br>This option is especially recommended for a very large directory to be read.<br>If the option is not active, the complete directory content is written into a single HTML file. Directories can then be expanded and collapsed by clicking on them.
- __Display hidden elements__<br>f this option is active, hidden files and directories are also listed in the HTML document. They are displayed in italics.
- __Display full paths__<br>If this option is active, all paths in the HTML document are displayed absolutely. If it is not active, the paths are shown relative to the selected directory.
- __Create tooltips__<br>If this option is active, additional information is displayed when the mouse hovers over a file or directory name in the HTML document.
- __Display program details__<br>If this option is active, additional information is displayed when the mouse hovers over a file or directory name in the HTML document.
- __Display file size__<br>If this option is active, the size of each file is displayed.
- __Display last change date__<br>If this option is active, the date of the last modification is shown for each file.
- __Display MP3 bitrate__<br>If this option is active, the bitrate of each MP3 file will be shown.
- __Create links__<br>If this option is active, all files in the HTML document are linked, i.e. they can be called directly from the HTML files. Of course, this only works if the HTML files are located on the computer where they were created.
- __Open links in new window__<br>If this option is active, file links will open in a new window. This option can only be selected if "Create links" is active.

## Customizing
You may customize the HTML output to your needs with HTML, CSS and JavaScript.

### HTML template
`data/template.html` is the main template. It contains these placeholders:
- `{TITLE}`: title of the document
- `{CSS}`: content of the stylesheet template file
- `{JS}`: content of the JavaScript template file
- `{HEADING}`: headline of the current view
- `{LISTING}`: listing of the directory contents
- `{PROGRAMINFO}`: some details of DirToHTML
### JavaScript template
`data/template.js` initially contains the toggle script, but you may extend it with any JavaScript code. The content of this file then replaces the placeholder `{JS}`.

### CSS template
`data/template.css` contains the CSS definitions for the HTML output. Its contents then replace the placeholder `{CSS}`.
