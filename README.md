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
