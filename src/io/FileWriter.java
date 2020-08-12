package io;

import util.Path;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileWriter {

    private static String FILE_NAME = "results.txt";
    private static String FILE_FOLDER = "src/io/files/";

    public static void writeToFile(Path p) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(FILE_FOLDER + FILE_NAME);
        pw.println(p.toString());
        pw.close();
    }
}
