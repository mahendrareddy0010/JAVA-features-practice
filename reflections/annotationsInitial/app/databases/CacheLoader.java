package reflections.annotationsInitial.app.databases;

import reflections.annotationsInitial.annotations.InitializerClass;
import reflections.annotationsInitial.annotations.InitializerMethod;

@InitializerClass
public class CacheLoader {
    @InitializerMethod
    public void loadCache() {
        System.out.println("Loading data from cache");
    }

    public void reloadCache() {
        System.out.println("Reload the cache");
    }
    
}
