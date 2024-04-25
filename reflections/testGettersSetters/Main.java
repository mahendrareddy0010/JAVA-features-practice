package reflections.testGettersSetters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import reflections.testGettersSetters.data.ClothingProduct;

public class Main {
    public static void main(String[] args) {
        testGetters(ClothingProduct.class);
        testSetters(ClothingProduct.class);
    }

    private static void testSetters(Class<?> dataClass) {
        List<Field> fields = getAllFields(dataClass);
        for (Field field : fields) {
            String setMethodName = "set" + capitalizeFirstLetter(field.getName());
            Method method = null;

            try {
                method = dataClass.getMethod(setMethodName, field.getType());
            } catch (NoSuchMethodException | SecurityException e) {
                throw new IllegalStateException(String.format("Setter : %s not found", setMethodName));
            }
            if (!method.getReturnType().equals(void.class)) {
                throw new IllegalStateException("Setter method should return void");
            }
        }

        System.out.println("Successfully passed Setters");
    }

    private static void testGetters(Class<?> dataClass) {
        List<Field> fields = getAllFields(dataClass);
        for (Field field : fields) {
            String getMethodName = "get" + capitalizeFirstLetter(field.getName());
            Method method = null;

            try {
                method = dataClass.getMethod(getMethodName);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new IllegalStateException(String.format("Getter : %s not found", getMethodName));
            }
            if (!method.getReturnType().equals(field.getType()) || method.getParameterTypes().length != 0) {
                throw new IllegalStateException("Getter method should return void");
            }
        }

        System.out.println("Successfully passed Getters");
    }

    private static List<Field> getAllFields(Class<?> dataClass) {
        List<Field> allFields = new ArrayList<>();
        if (dataClass == null) {
            return allFields;
        }
        Field[] currentClassFields = dataClass.getDeclaredFields();
        List<Field> inheritedFields = getAllFields(dataClass.getSuperclass());
        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);

        return allFields;
    }

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
