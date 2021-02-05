package ru.sbrf;

import ru.sbrf.annotation.After;
import ru.sbrf.annotation.Before;
import ru.sbrf.annotation.Test;

public class Example {

        private String name = "nothing";

        @Before
        public void before1(){ System.out.println("before1 did '" + name + "'");
        }

        @After
        public void after1(){
            System.out.println("after1 did '" + name + "'");
        }

        @After
        public void after2() { System.out.println("after2 did '" + name + "'"); }

        public void after3() { System.out.println("after3 did '" + name + "'"); }

        @Test
        public void test1(){
            System.out.println("test1 did '" + name + "'");
        }

        @Test
        public void test2(){
                System.out.println("test2 did '" + name + "'");
        }

        @Test
        public void test3(){
                throw new RuntimeException("I'm failed");

                //System.out.println("test2 did '" + name + "'");
        }

        public void test4(){
                System.out.println("test3 did '" + name + "'");
        }

}
