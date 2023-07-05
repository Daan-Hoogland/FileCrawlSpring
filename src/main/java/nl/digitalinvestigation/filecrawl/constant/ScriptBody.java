package nl.digitalinvestigation.filecrawl.constant;

public class ScriptBody {

    public static final String FILE_ENDPOINT = "/hits/file/create";
    public static final String DIRECTORY_ENDPOINT = "/hits/directory/create";

    public static final String BASH_HEADER = "#!/bin/bash\n";
    public static final String BASH_COMMAND = "./bashcrawler -t %s -q %d -e %s ";
    public static final String BASH_HASH = "-h %s ";
    public static final String BASH_HASH_ALGORITHM = "-a %s ";
    public static final String BASH_PATH = "-p %s ";
    public static final String BASH_NAME = "-n %s ";
    public static final String BASH_SIZE = "-s %d ";
    public static final String BASH_QUEUE = "&\n";
    public static final String BASH_QUEUE_REST = "wait; ";

    public static final String POWERSHELL_COMMAND = ".\\PSCrawler.ps1 -target %s -query %d -execute %s ";
    public static final String POWERSHELL_HASH = "-hash %s ";
    public static final String POWERSHELL_PATH = "-path %s ";
    public static final String POWERSHELL_NAME = "-name %s ";
    public static final String POWERSHELL_SIZE = "-size %d ";
    public static final String POWERSHELL_HASH_ALGORITHM = "-algorithm %s ";
    public static final String POWERSHELL_QUEUE = "&";

    public static final String JAVA_COMMAND = "java -jar JavaCrawler.jar -t %s -q %d ";
    public static final String JAVA_HASH = "-h %s ";
    public static final String JAVA_PATH = "-p %s ";
    public static final String JAVA_NAME = "-n %s ";
    public static final String JAVA_SIZE = "-s %d ";

    public static final String BATCH_HEADER = "@echo off\n";

    public static final String POWERSHELL_JAVA = "Start-Process -FilePath java -ArgumentList ";
    public static final String POWERSHELL_JAVA_WAIT = "$proc.WaitForExit()";

}
