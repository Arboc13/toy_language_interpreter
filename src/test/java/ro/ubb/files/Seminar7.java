package ro.ubb.files;

import java.util.List;

public class Seminar7 {

    List<String> words = List.of("hi", "hello", "bye", "goodbye", "java", "stream",
            "lambda", "map", "filter", "reduce", "eye", "queue", "collect", "Hello");
    record Student(String name, int group, double grade) {}
    List<Student> students = List.of(
            new Student("Ana", 921, 9.5), new Student("Bogdan", 922, 4.0),
            new Student("Carmen", 921, 7.0), new Student("Dan", 923, 8.5),
            new Student("Elena", 922, 10.0), new Student("Florin", 923, 5.0),
            new Student("Gabriela", 921, 3.5), new Student("Horia", 922, 6.5));
    String text = "The stream API is lazy. A stream is not a collection: "
            + "the stream is consumed once, and the collection is not.";
}
