package customSerializeDeserialize;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        Sample sample = new Sample();
        String serializedString = Serialize.serialize(sample);
        System.out.println(serializedString);
        Sample newSample = (Sample) Deserialize.deserialize(serializedString);
        System.out.println(newSample);
    }
}
