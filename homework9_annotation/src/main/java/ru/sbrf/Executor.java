package ru.sbrf;

import ru.sbrf.annotation.After;
import ru.sbrf.annotation.Before;
import ru.sbrf.annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Executor {
    public void runAnnotation(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Constructor constructor = clazz.getConstructor();

        int executeMethods = 0;
        int badMethods = 0;
        int goodMethods = 0;

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class))
                beforeMethods.add(method);
            if (method.isAnnotationPresent(After.class))
                afterMethods.add(method);
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Object example = constructor.newInstance();
                //System.out.println(afterMethods);
                for (Method methodB : beforeMethods) {
                    try { //Run @Before
                        methodB.invoke(example);
                        //executeMethods++;
                    } catch (Exception e) {
                        //badMethods++;
                    }
                    try { //Run @Test
                        executeMethods++;
                        method.invoke(example);
                        goodMethods++;
                    } catch (Exception e) {
                        badMethods++;
                    }
                    for (Method methodA : afterMethods) {
                        try { //Run @After
                            methodA.invoke(example);
                            //  executeMethods++;
                        } catch (Exception e) {
                            // badMethods++;
                        }
                    }
                }
            }
        }
        System.out.println("——————————————————————————————————————————————");
        System.out.println("Всего выполнено тестов: " + executeMethods);
        System.out.println("Всего выполнено успешно: " + goodMethods);
        System.out.println("Всего выполнено с ошибкой: " + badMethods);
    }

}
