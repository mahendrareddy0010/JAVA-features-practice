package reflections.annotationsInitial.app.configs;

import reflections.annotationsInitial.annotations.InitializerClass;
import reflections.annotationsInitial.annotations.InitializerMethod;

@InitializerClass
public class ConfigsLoader {
    @InitializerMethod
    public void loadAllconfigFiles() {
        System.out.println("Loading all config files");
    }
}
