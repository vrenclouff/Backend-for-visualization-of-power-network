package cz.zcu.kiv.vps.core.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Lukas Cerny.
 *
 * Class localizations key to message.
 */
public class Messages {

    private static final ResourceBundle messages;

    static {

        messages  = ResourceBundle.getBundle("LANG/Messages", Locale.getDefault());
    }

    /**
     * Localization key to message
     * @param key to localization
     * @return localization message
     */
    public static String getString(String key) {
        try {
            return messages.getString(key);
        }catch (NullPointerException | MissingResourceException e) {
            return "!" + key + "!";
        }
    }

    /**
     * Localization key to message with parameters.
     * @param key to localization
     * @param parameters
     * @return localization message
     */
    public static String getStringWithFormat(String key, String ... parameters) {
        try {
            String format = messages.getString(key);
            return MessageFormat.format(format, parameters);
        }catch (NullPointerException | MissingResourceException e) {
            return "!" + key + "!";
        }
    }
}
