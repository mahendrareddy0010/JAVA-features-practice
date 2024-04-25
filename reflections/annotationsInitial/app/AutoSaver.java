package reflections.annotationsInitial.app;

import reflections.annotationsInitial.annotations.InitializerClass;
import reflections.annotationsInitial.annotations.InitializerMethod;

@InitializerClass
public class AutoSaver {
    @InitializerMethod
    public void startAutoSavingThreads(){
        System.out.println("Start saving data automaticaly to disk");
    }
}
