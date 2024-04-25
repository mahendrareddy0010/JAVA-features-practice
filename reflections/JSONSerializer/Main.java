package reflections.JSONSerializer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import reflections.JSONSerializer.data.Actor;
import reflections.JSONSerializer.data.Address;
import reflections.JSONSerializer.data.Company;
import reflections.JSONSerializer.data.Movie;
import reflections.JSONSerializer.data.Person;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        // Person person = new Person("Idiot", 10, new Address("Heaven", 12),
        // new Company("company-1", "Hyderabad", new Address("street-1", 3)), 10000);
        Actor actor1 = new Actor("Elijah Wood", new String[] { "Lord of the Rings", "The Good Son" });
        Actor actor2 = new Actor("Ian McKellen", new String[] { "X-Men", "Hobbit" });
        Actor actor3 = new Actor("Orlando Bloom", new String[] { "Pirates of the Caribbean", "Kingdom of Heaven" });

        Movie movie = new Movie("Lord of the Rings", 8.8f, new String[] { "Action", "Adventure", "Drama" },
                new Actor[] { actor1, actor2, actor3 });

        String json = objectToJson(movie, 0);

        System.out.println(json);
    }

    public static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("{");
        stringBuilder.append("\n");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if (field.isSynthetic()) {
                continue;
            }

            stringBuilder.append(indent(indentSize + 1));
            stringBuilder.append(formatStringValue(field.getName()));

            stringBuilder.append(":");

            if (field.getType().isPrimitive()) {
                stringBuilder.append(formatPrimitiveValue(field.getType(), field.get(instance)));
            } else if (field.getType().equals(String.class)) {
                stringBuilder.append(formatStringValue(field.get(instance).toString()));
            } else if (field.getType().isArray()) {
                stringBuilder.append(arrayToJson(field.get(instance), indentSize));
            } else {
                stringBuilder.append(objectToJson(field.get(instance), indentSize + 1));
            }

            if (i != fields.length - 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String arrayToJson(Object arrayInstance, int indentSize) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();

        int arrayLength = Array.getLength(arrayInstance);
        Class<?> componentType = arrayInstance.getClass().getComponentType();

        stringBuilder.append("[");
        stringBuilder.append("\n");
        for (int i = 0; i < arrayLength; i = i + 1) {
            Object element = Array.get(arrayInstance, i);
            if (componentType.isPrimitive()) {
                stringBuilder.append(indent(indentSize+1));
                stringBuilder.append(formatPrimitiveValue(componentType, element));
            } else if (componentType.equals(String.class)) {
                stringBuilder.append(indent(indentSize+1));
                stringBuilder.append(formatStringValue((String) element));
            } else {
                stringBuilder.append(objectToJson(element, indentSize + 1));
            }
            if (i != arrayLength - 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(indent(indentSize));
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    private static String indent(int indentSize) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indentSize; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Class<?> type, Object instance) throws IllegalAccessException {
        if (type.equals(boolean.class)
                || type.equals(int.class)
                || type.equals(long.class)
                || type.equals(short.class)) {
            return instance.toString();
        } else if (type.equals(double.class) || type.equals(float.class)) {
            return String.format("%.02f", instance);
        }

        throw new RuntimeException(String.format("Type : %s is unsupported", type.getName()));
    }

    private static String formatStringValue(String value) {
        return String.format("\"%s\"", value);
    }
}
