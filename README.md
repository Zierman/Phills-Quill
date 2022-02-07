# Phill's Quill
Phill's Quill is a tool aimed to take a .docx file and output plain text in a hard-wrapped format.

## Installation
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

## Usage
The following examples assume you set up a `phillsquill` command like shown in the instillation steps above.
If not, just replace `phillsquill` with `java -jar <path-to-phillsquill-jar>` where `<path-to-phillsquill-jar>` is a placeholder for the path to wherever you placed the jar file.

### Running the program in GUI Mode
Launching the jar file without providing any arguments should open the application in GUI mode. Alternitively you can use the `-g` argument to run in GUI mode as well which allows you to provide other arguments (see Convert .docx File To Plain Text via command line secton).

If you want to launch the program in auto-conversion mode, use the `-a` option. This will make it so when you load a new document it will be folded without needing to press the convert button.

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
