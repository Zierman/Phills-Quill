# Phill's Quill
Phill's Quill is a tool aimed to take a .docx file and output plain text in a hard-wrapped format.

## Installation
Prerequisit: You must have a recent version of Java (15+) installed to run this program. Visit [the java download page](https://www.oracle.com/java/technologies/downloads/) if you need to install Java.
1. Navigate to [the releases](https://github.com/Zierman/phillsquill/releases) for this project.
2. Choose the version of the software you are interested in.
3. Under assets for the desired version download the jar file (ends in ".jar").
4. Move that jar file wherever you want it on your filesystem.
5. \[Optional Advanced Step\] Make a command such as `pq` or `phillsquill` to make the program easier to execute.
    - This step is system dependent and be completed in a number of ways other than what is shown here.
    - I like to create an alias in my profile for my shell like this, where `<path-to-phillsquill-jar>` is a placeholder you need to replace with the absolute path to the jar file: 
```shell
    alias phillsquill="java -jar <path-to-phillsquill-jar>"
```

## Usage-GUI


### Launching the Program
An easy way to run the program is to just double-click on the jar file. 

Another way is to use the `-g` option when launching the program from a script or shell. 
See the [CLI usage section](#usage-cli) for more info.

### Opening a .docx file
In the menu bar click on **File**, then click **Open...**. 

Alternatively you can use the shortcut:
- Windows: Control + O
- Mac: Command + O

The system will open up a dialog to allow you to choose a file. Once you find the file you wish to open press the Open button.

The document will be converted to plain text and shown in the output text area. If the [auto convert preference](#auto-convert) is checked, it will also perform hard wrapping automatically.

### Saving a plain text file
In the menu bar click on **File**, then click **Save...**.

Alternatively you can use the shortcut:
- Windows: Control + S
- Mac: Command + S

The system will open up a dialog to allow you to choose a file. Once you enter the filename you wish to save as, press the Save button.

### Copy All
If you wish to quickly copy all output text to the clipboard, click **Edit** in the menu bar and then click **Copy All**.

Alternatively you can use the shortcut:
- Windows: Control + Shift + C
- Mac: Command + Shift + C

### Preferences
To access preferences click **Edit** in the menu bar and then click **Preferences...**.

#### Default Output Width
The default output width is the initial value that will be used in the output width field when running the program 
without the `-w` option as well as the value that will be used when clicking the reset button.

The value must be a positive integer.

#### Auto Convert
When the Auto Convert preference is checked, conversions will happen automatically when opening a new document or 
resetting the output width field.

## Usage-CLI
The following examples assume you set up a `phillsquill` command like shown in the instillation steps above.
If not, just replace `phillsquill` with `java -jar <path-to-phillsquill-jar>` where `<path-to-phillsquill-jar>` is a placeholder for the path to wherever you placed the jar file.

### Show Help
To show a help message, you can run this:
```shell
phillsquill -h
```

### Convert .docx File To Plain Text via command line
Providing the file's path with no other arguments will convert to text with the default width.

linux/mac example:
```shell
phillsquill "/some/place/example.docx"
```

windows example:
```shell
phillsquill "C:\some\place\example.docx"
```

_Future examples will provide the file path as a relative path to a file located in the present working directory._

### Defining output width
By default, the output width is 80 characters, but you can define a width with `-w`:

40 character width example:
```shell
phillsquill -w 40 "example.docx"
```
