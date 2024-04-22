package customSerializeDeserialize;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Deserialize {
    public static Object deserialize(String codedString)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        String[] fields = codedString.split(",");
        Class<?> clazz = Class.forName(fields[0]);
        Object object = clazz.getConstructor().newInstance();
        for (int i = 1; i < fields.length; i = i + 1) {
            String[] fieldInfo = fields[i].split(":");
            String fieldName = fieldInfo[0];
            String fieldValue = fieldInfo[1];
            Field field = clazz.getDeclaredField(fieldName);
            field.set(object, parseValue(field.getType(), fieldValue));
        }

        return object;
    }

    private static Object parseValue(Class<?> type, String value) {
        if (type.equals(int.class)) {
            return Integer.parseInt(value);
        }
        if (type.equals(double.class)) {
            return Double.parseDouble(value);
        }
        if (type.equals(String.class)) {
            return value;
        }

        return value;
    }
}
