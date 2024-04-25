package reflections.classDemonstration;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<String> stringClass = String.class;
        Map<Integer, String> mapObject = new HashMap<>();
        Class<?> squareClass = Class.forName("reflections.classDemonstration.Main$Square");

        var circleObject = new Drawable() {
            @Override
            public void draw() {
                System.out.println("Circle drawn");
            }
        };

        printClassInfo(stringClass, mapObject.getClass(), squareClass, Color.class, Drawable.class, circleObject.getClass());
    }

    private static void printClassInfo(Class<?> ...classes) {
        for (Class<?> clazz : classes) {
            System.out.println(String.format("Class Name : %s\nPackage Name : %s", clazz.getSimpleName(), clazz.getPackageName()));
            Class<?>[] implementedInterfaces = clazz.getInterfaces();
            System.out.println("Implemented Interfaces : ");
            for (Class<?> implementedInterface : implementedInterfaces) {
                System.out.println("        " + implementedInterface.getSimpleName());
            }
            System.out.println("Is primitive : " + clazz.isPrimitive());
            System.out.println("Is array : " + clazz.isArray());
            System.out.println("Is enum : " + clazz.isEnum());
            System.out.println("Is interface : " + clazz.isInterface());
            System.out.println("Is anonymous : " + clazz.isAnonymousClass());
            System.out.println();
            System.out.println();
        }
    }

    private enum Color {
        RED, BLUE, GREEN, BLACK
    }

    private static interface Drawable {
        public void draw();
    }

    private static class Square implements Drawable {
        public Square(){}

        @Override
        public void draw() {
            System.out.println("Square drawn");
        }
    }
}
