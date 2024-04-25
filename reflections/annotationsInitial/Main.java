package reflections.annotationsInitial;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reflections.annotationsInitial.annotations.InitializerClass;
import reflections.annotationsInitial.annotations.InitializerMethod;
import reflections.annotationsInitial.annotations.RetryOperation;
import reflections.annotationsInitial.annotations.ScanPackages;

@ScanPackages({ "app", "app.configs", "app.databases", "app.http" })
public class Main {
    public static void main(String[] args)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            ClassNotFoundException, InstantiationException, IllegalArgumentException, URISyntaxException, IOException, InterruptedException {
        initialize();
    }

    private static void initialize() throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, SecurityException, ClassNotFoundException, URISyntaxException, IOException,
            InstantiationException, IllegalArgumentException, InterruptedException {
        String[] scannedPackages = Main.class.getAnnotation(ScanPackages.class).value();
        if (scannedPackages.length == 0) {
            return;
        }
        List<Class<?>> allClasses = getAllClasses(scannedPackages);
        for (Class<?> clazz : allClasses) {
            if (!clazz.isAnnotationPresent(InitializerClass.class)) {
                continue;
            }
            Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Method method : getAllInitializingMethods(clazz)) {
                callInitializingMethods(instance, method);
            }
        }
    }

    private static void callInitializingMethods(Object instance, Method method)
            throws IllegalAccessException, InvocationTargetException, InterruptedException {
        if (method.isAnnotationPresent(RetryOperation.class)) {
            int numberOfRetries = method.getAnnotation(RetryOperation.class).numberOfRetries();
            long durationBetweenRetriesMs = method.getAnnotation(RetryOperation.class).durationBetweenRetriesMs();
            while (numberOfRetries > 0) {
                try {
                    method.invoke(instance);
                    break;
                } catch (InvocationTargetException e) {
                    // System.out.println("Retrying after : " + durationBetweenRetriesMs);
                    Thread.sleep(durationBetweenRetriesMs);
                } finally {
                    numberOfRetries -= 1;
                }
            }
        } else {
            method.invoke(instance);
        }
    }

    private static List<Method> getAllInitializingMethods(Class<?> clazz) {
        List<Method> initializingMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitializerMethod.class)) {
                initializingMethods.add(method);
            }
        }

        return initializingMethods;
    }

    private static List<Class<?>> getAllClasses(String... packageNames)
            throws URISyntaxException, ClassNotFoundException, IOException {
        List<Class<?>> allClasses = new ArrayList<>();

        for (String packageName : packageNames) {
            String packageRelativePath = packageName.replace('.', '/');

            URI packageUri = Main.class.getResource(packageRelativePath).toURI();

            if (packageUri.getScheme().equals("file")) {
                Path packageFullPath = Paths.get(packageUri);
                allClasses.addAll(getAllPackageClasses(packageFullPath, packageName));
            } else if (packageUri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(packageUri, Collections.emptyMap());

                Path packageFullPathInJar = fileSystem.getPath(packageRelativePath);
                allClasses.addAll(getAllPackageClasses(packageFullPathInJar, packageName));

                fileSystem.close();
            }
        }

        return allClasses;
    }

    private static List<Class<?>> getAllPackageClasses(Path packagePath, String packageName)
            throws IOException, ClassNotFoundException {
        if (!Files.exists(packagePath)) {
            return Collections.emptyList();
        }
        List<Path> allFiles = Files.list(packagePath).filter(file -> Files.isRegularFile(file)).toList();
        List<Class<?>> classes = new ArrayList<>();
        for (Path filePath : allFiles) {
            String fileName = filePath.getFileName().toString();

            if (fileName.endsWith(".class")) {
                String fullClassName = packageName.isBlank() ? fileName.replaceFirst("\\.class$", "")
                        : packageName + "." + fileName.replaceFirst("\\.class$", "");

                Class<?> clazz = Class.forName("reflections.annotationsInitial." + fullClassName);
                classes.add(clazz);
            }
        }

        return classes;
    }
}
