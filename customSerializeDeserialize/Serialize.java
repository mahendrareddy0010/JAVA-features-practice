package customSerializeDeserialize;

import java.lang.reflect.Field;

public class Serialize {
    public static String serialize(Object object) throws IllegalArgumentException, IllegalAccessException {
        String className = object.getClass().getTypeName();
        StringBuilder codedString = new StringBuilder();
        codedString.append(className);
        codedString.append(",");
        for (Field field : object.getClass().getDeclaredFields()) {
            codedString.append(field.getName());
            codedString.append(":");
            codedString.append(field.get(object).toString());
            codedString.append(",");
        }

        return codedString.deleteCharAt(codedString.length() - 1).toString();
    }
}
