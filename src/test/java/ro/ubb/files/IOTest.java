package ro.ubb.files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ClassTemplateInvocationLifecycleMethod;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

public class IOTest {
/*
    @Test
    void test() throws IOException {



        //File file = new File("path/example"); // basically an abstract implementation of a directory's path
        var printWriter = new PrintWriter("test.out"); //can be linked with other writers and write to them

        try {
            printWriter.printf("Hello %d", 13);
        } finally {
            printWriter.close();
        }// can be written with try with resources
        // can put more files in the try
        // if we put it as a try argument it will automatically close it when done


        try (PrintWriter writer = new PrintWriter("test2.out")) {
            writer.printf("Hello 3\n");
        }




        FileOutputStream fos = null;
        OutputStream outputStream;

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("out.txt")));
        pw.println("result: " + 42);
        // end of main – out.txt is emp


        /*
        * void log(String msg) throws IOException { // called after every step
        try (var w = new FileWriter("log.txt")) {
            w.write(msg + "\n");
            }
        } // log.txt keeps one line only, we need to make the append flag true in order for it to be fine
        * */
    /*}

    @Test
    void problemTest( ) throws IOException {
        byte [] buffer = new byte[1<<13];
        try (var inputStream = new FileInputStream("gradlew");
            var outputStream = new FileOutputStream("temp.out")
        ){
            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0, bytesRead);
            }
        }
    }*/
}
