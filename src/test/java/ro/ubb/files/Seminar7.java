package ro.ubb.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Seminar7 {

    public class MyConsumer implements Consumer<String>{

        @Override
        public void accept(String s) {
            IO.println("  " + s);
        }
    }

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

    @Test
    void test1(){
        for (var word : words){
            IO.println("  " + word);
        }
    }

    @Test
    void test2(){
        words.forEach(new MyConsumer());
    }

    @Test
    void test3(){
        words.forEach((String word) -> {IO.println("  " + word);});
    }

    @Test
    void test4(){
        words.forEach(IO::println);
    }

    List<String> transformedList(List<String> list, Function<String, String> function){

        return list.parallelStream().map(function).toList(); // parallel processing!!!

        //return list.stream().map(function).collect(Collectors.toList());
    }


    interface Function1<T> { T app(T t); }
    List<String> excitingWords =
            transformedList(words, s -> s + "!");
    List<String> eyeWords =
            transformedList(words, s -> s.replace("i", "eye"));
    List<String> upperCaseWords =
            transformedList(words, String::toUpperCase);


    List<String> allMatches(List<String> list, Predicate<String> cond){
            return list.stream().filter(cond).toList();
    }
    List<String> shortWords =allMatches(words, s -> s.length() < 4);
    List<String> wordsWithB = allMatches(words, s -> s.contains("b"));
    List<String> evenLengthWords = allMatches(words, s -> s.length() % 2 == 0);

    String probSolve(String c){
        return words.stream()
                .map(String::toUpperCase)
                .filter(s -> s.length() < 4)
                .filter(s -> s.contains(c))
                .findFirst()
                //.ifPresent(IO::println);
                .orElse(null);
    }

    @Test
    void testProblem5(){
        String result = probSolve("E");
        String result2 = probSolve("Q");
        IO.println(probSolve("B"));

        Assertions.assertEquals("BYE", result);
        Assertions.assertEquals(null, result2);

    }

}
