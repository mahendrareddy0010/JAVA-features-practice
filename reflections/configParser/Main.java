package reflections.configParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchFieldException, IOException {
        final String PATH_TO_GAME_CONFIG = "reflections/configParser/resources/game_properties.cfg";
        final String PATH_TO_USER_INTERFACE_CONFIG = "reflections/configParser/resources/user_interface_properties.cfg";

        GameConfig gameConfig = createObjectFromConfig(GameConfig.class, PATH_TO_GAME_CONFIG);
        UserInterfaceConfig userInterfaceConfig = createObjectFromConfig(UserInterfaceConfig.class,
                PATH_TO_USER_INTERFACE_CONFIG);

        System.out.println(gameConfig);
        System.out.println(userInterfaceConfig);
    }

    private static <T> T createObjectFromConfig(Class<T> clazz, String path)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchFieldException, IOException {
        T configObject = clazz.getConstructor().newInstance();

        Scanner sc = new Scanner(Path.of(path));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] args = line.split("=");
            if (args.length != 2) {
                continue;
            }
            String fieldName = args[0];
            String fieldValue = args[1];

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().isArray()) {
                field.set(configObject, parseArray(field.getType(), fieldValue));
            } else {
                field.set(configObject, parseFieldValue(field.getType(), fieldValue));
            }
        }
        sc.close();

        return configObject;
    }

    private static Object parseArray(Class<?> type, String arrayInstance) {
        String[] arrayElements = arrayInstance.split(",");
        Class<?> componentType = type.getComponentType();
        Object arrayObject = Array.newInstance(componentType, arrayElements.length);

        for (int i = 0; i < arrayElements.length; i = i + 1) {
            Array.set(arrayObject, i, parseFieldValue(componentType, arrayElements[i]));
        }

        return arrayObject;
    }

    private static Object parseFieldValue(Class<?> type, String fieldValue) {
        if (type.equals(int.class)) {
            return Integer.parseInt(fieldValue);
        } else if (type.equals(short.class)) {
            return Short.parseShort(fieldValue);
        } else if (type.equals(long.class)) {
            return Long.parseLong(fieldValue);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(fieldValue);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(fieldValue);
        } else if (type.equals(String.class)) {
            return fieldValue;
        }
        throw new RuntimeException(String.format("Type : %s unsupported", type.getTypeName()));
    }
}
