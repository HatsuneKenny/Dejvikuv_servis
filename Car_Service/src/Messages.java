import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Messages {
    private static ResourceBundle bundle;

    static {
        setLocale(new Locale("cs", "CZ"));
    }

    public static void setLocale(Locale locale) {
        System.out.println("Switching language to: " + locale);
        bundle = ResourceBundle.getBundle("messages", locale, new UTF8Control());
    }


    public static String get(String key) {
        return bundle.getString(key);
    }

    private static class UTF8Control extends ResourceBundle.Control {
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            try (InputStreamReader reader = new InputStreamReader(loader.getResourceAsStream(resourceName), StandardCharsets.UTF_8)) {
                return new PropertyResourceBundle(reader);
            }
        }
    }
}
