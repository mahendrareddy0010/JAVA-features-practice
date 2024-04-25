package reflections.constructors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        printConstructorsData(Person.class);

        Address address = createInstanceWithArguments(Address.class, "street-1", 12);
        Person person = createInstanceWithArguments(Person.class, "Idiot", 10);
        System.out.println(address);
        System.out.println(person);
    }

    private static <T> T createInstanceWithArguments(Class<T> clazz, Object ...args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object newObj = null;
        for(Constructor<?> constructor: clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == args.length) {
                newObj =  constructor.newInstance(args);
            }
        }

        return (T) newObj;
    }

    private static void printConstructorsData(Class<?> clazz) {
        System.out.println("Class constructors count : " + clazz.getDeclaredConstructors().length);

        for(Constructor<?> constructor: clazz.getDeclaredConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            List<String> parameterTypeNames = Arrays.stream(parameterTypes).map((type) -> type.getSimpleName()).toList();
            System.out.println(parameterTypeNames);
        }
    }

    private static class Person {
        private final String name;
        private final int age;
        private final Address address;

        public Person() {
            this.name = "anonymous";
            this.age = 0;
            this.address = null;
        }

        public Person(String name) {
            this.name = name;
            this.age = 0;
            this.address = null;
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
            this.address = null;
        }

        public Person(String name, int age, Address address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        @Override
        public String toString() {
            return "Person [name=" + name + ", age=" + age + ", address=" + address + "]";
        }
    }

    private static class Address {
        private String street;
        private int number;

        public Address(String street, int number) {
            this.street = street;
            this.number = number;
        }

        @Override
        public String toString() {
            return "Address [street=" + street + ", number=" + number + "]";
        }
    }
}
