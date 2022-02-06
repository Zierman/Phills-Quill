# Phill's Quill
Phill's Quill is a tool aimed to take a .docx file and output the text in a hard-wrapped format.

## Installation
1. Navigate to [the releases](https://github.com/Zierman/phillsquill/releases) for this project.
2. Choose the version of the software you are interested in.
3. Under assets for the desired version download the jar file (ends in ".jar").
4. Move that jar file wherever you want it on your filesystem.
5. \[Optional Advanced Step\] Make a command such as `pq` or `phillsquill` to make the program easier to execute.
    - This step is system dependent and be completed in a number of ways other than what is shown here.
    - I like to create an alias in my profile for my shell like this: 
```shell
    alias phillsquill="java -jar <path-to-phillsquill-jar>"
```
6. 

## Usage
The following steps assume you set up a `phillsquill` command like shown in the instillation steps above.

### Show Help
To show a help message, you can run this:
```shell
phillsquill -h
```

### Convert .docx File To Plain Text
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
