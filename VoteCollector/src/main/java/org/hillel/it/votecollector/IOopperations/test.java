package org.hillel.it.votecollector.IOopperations;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 21.01.14
 * Time: 17:18
 */
public class test {

    public static void main(String[] args){
            A a = new B();
            a.b();
        }
    }

    class A {
        void a() {
            System.out.println("A-a");
        }

        void b() {
            System.out.println("A-b");
            this.a();
        }
    }

    class B extends A {
        void a() {
            System.out.println("B-a");
        }

        void b() {
            System.out.println("B-b");
            super.b();
        }
    }