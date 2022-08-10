# VINExtractor
A program created for extracting VIN numbers from paperwork and comparing them to ones loaded from specific CSV files

![screenshot](https://user-images.githubusercontent.com/85239440/183917535-6db62d87-abe6-4ca2-99bd-e05c7944c498.png)

Program needs JAVA SE 16+ version installed on a system.
I recommend Oracle 18 SE JDK(includes JRE by default)

//////////////////////////////////////////////////////////////

DON'T MOVE TESSDATA FOLDER FROM JAR DIRECTORY, IT BREAKS TEXT RECOGNITION 

//////////////////////////////////////////////////////////////


This program is created with an functionality of Extracting text
from images with a help of Tesseract OCR Engine.

You may load Reports first, and the keep extracting VINs.

Basic usage instructions:

1.
- Load BOL image renamed with driver name to a program by clicking "Load BOL image",
or dragging and dropping an image to an designated image area.

2.
- Crop each VIN number from a BOL, one at a time.
- You may crop same VIN number multiple times and load it into a program,
as duplicate values won't affect comparison results.

3.
- Verify that the text is painted green in , as that means that loaded
VIN has 17 characters exactly. Even if it doesn't, program will
recongize it as valid afterwards if the last 8 characters are correctly read.

4.
Load wanted Reports. 
- For loading report taken from scanner for all units, click on "Load CSV Report"
while "All units" value in a ChoiceBox is selected.
- For loading report taken from Mazda scanner, one must first save it
as .CSV (Convert from XLSX format to CSV). Then click on "Load CSV Report"
while "Mazda Units" option from ChoiceBox is selected.

5.Just be sure that all Report VIN column values are green at the end.

------ TABLE VALUE COLOR CODING -------

- BOL VIN Column
Red: Value is not exactly 17 characters long
Black: Value is 17 characters long

- Report VIN Column
Green: VIN Match is found
Black: VIN Match not found yet - Try to load that VIN once again or compare it manually


Additional functionalities:

- Delete Table deletes both Extracted data and data loaded from reports.
- Clear fields and Append to Table is self explanatory.

This program has two modes: Image text Extraction and Bar Code Reader mode.

- Image text Extraction mode is capable of extracting text from Image
in a single line.
- Vin Auto Correction processes text afterwards in a way that makes
text Extraction more accurate. It removes whitespaces,brackets and curly brackets,
replaces "O" with 0, "l" with "1" and much more.
-----Unchecking it will output raw extracted data!------

- Bar Code Reader mode can scan and recongize multiple Bar Codes
at a time, making Bar Code recognition much faster and
reliable option.

- Export Extracted Data button
- - It Exports only the driver name and VIN from BOL image to a CSV.
(May be handy to developers)

- Export Complete Table To Excel
- - Exports whole table to CSV format.
(May be handy to developers)

- Limit Mouse is Experimental function. Turning on may result in bugs


External libraries used:
Tesseract(Text image Exctraction)
OpenCV(image proccesing)
OpenCSV(loading and writing to a CSV)
ZXing(barcodes)
