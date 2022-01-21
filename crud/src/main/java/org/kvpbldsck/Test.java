package org.kvpbldsck;

import java.io.*;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Test {

    enum PC {A1, A2, A3, A4, A5, A6, A7};

    static class Book {
        int a;

        public Book(int a) {
            this.a = a;
        }

        public int hashCode() {return 2;}
    }

    class A {}
    class B extends A implements Serializable {}

    public Test(int i) {
        this(i, i);
        System.out.println(1);
    }

    public Test(int i, int j) {
        this();
        System.out.println(1);
    }

    public Test() {
        System.out.println(1);
    }

    public static void main(String[] args) throws IOException {

        EnumSet<PC> e1 = EnumSet.range(PC.A3, PC.A5);
        var e2 = EnumSet.complementOf(e1);
        System.out.println(e2);

        Book b1 = new Book(1);
        Book b2 = new Book(1);
        Set<Book> set = new HashSet<>();
        set.add(b1);
        set.add(b2);

        System.out.println(set.size());
        System.out.println("*********");

        var f = new File("");
        var obj1 = new PrintWriter(f);

        var sc = new Scanner(System.in);
        int x1 = sc.nextInt();
        int x2 = System.in.read();
        System.out.println(x1 + " " + (char)x2);

        Pattern p = Pattern.compile("(1*)0");
        var m = p.matcher("111110");
        System.out.println(m.group(1));



        String s2 = new String("Minsk");
        String s1 = "Minsk";
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s2.intern()));
        System.out.println(s1.intern() == s2);

        if (true)
            if (true) System.out.println("a");
        else System.out.println("b");
        else;

        double[] mas [] = new double[4][];

        double x = 0, y = 2, z = y / x;

        new Test(1, 2);
    }
}
