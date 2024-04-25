package reflections.annotationsInitial.app.http;

import reflections.annotationsInitial.annotations.InitializerClass;

@InitializerClass
public class ServiceRegistry {
    public void registerService() {
        System.out.println("Service registered successfully");
    }
}
