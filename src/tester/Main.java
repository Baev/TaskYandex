package tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner reader = new Scanner(file);
            try {
                PrintWriter writer = new PrintWriter("output.txt");
                try {


                    String url = reader.next();
                    Tester tester = new Tester();
                    tester.run(url, writer);

                } finally {
                    writer.close();
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }
}
