package org.vimarsha.mediator;

/**
 * Created with IntelliJ IDEA.
 * User: sunimal
 * Date: 11/16/13
 * Time: 10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class UIHandler {
    private static UIHandler ourInstance = new UIHandler();

    public static UIHandler getInstance() {
        return ourInstance;
    }

    private UIHandler() {
    }
}
