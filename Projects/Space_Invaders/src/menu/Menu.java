package menu;

import animations.Animation;
/**
 * menu interface - can add selections and sub-menus.
 * implement animation
 * @author Omry Zur
 *
 * @param <T> - the return type from options
 */
public interface Menu<T> extends Animation {

    /**
     * Add selection to the menu.
     * @param key - the key for the option
     * @param message - the title of the option
     * @param returnVal - the value return from the option select
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return - the seleced option.
     */
    T getStatus();
}
