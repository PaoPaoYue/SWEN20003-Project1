package shadowbuild.sprite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class SpritesParser {
    private static Map<String, Class<? extends Sprite>> spritesMap = new HashMap<>();

    static {
        try {
            Class[] spriteClasses = getClasses(Sprite.class.getPackage().getName());
            for (Class spriteClass: spriteClasses) {
                if(Sprite.class.isAssignableFrom(spriteClass) &&
                        !Modifier.isAbstract(spriteClass.getModifiers())){
                    spritesMap.put(spriteClass.getSimpleName(), spriteClass);
                }

            }
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            System.out.println("SpriteParseError: initialize failed!!!");
            e.printStackTrace();
        };

    }

    public static Class<? extends Sprite> getSpriteClass(String spriteClassName) {
        if (spritesMap.containsKey(spriteClassName))
            return spritesMap.get(spriteClassName);
        else {
            System.out.println("SpriteParseError: class name not found!!!");
            return null;
        }
    }


    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.toURI()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[0]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
