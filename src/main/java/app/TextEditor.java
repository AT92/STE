package app;

import app.controller.EditorController;

/**
 * This is the main class of the Security Text Editor.
 * It initializes the controller and contains the main method.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
final class TextEditor {
    /**
     * This is the main method of the programm, it initializes the controller.
     * @param args, the command line parameter.
     */
    public static void main(String[] args) {
        new EditorController();
    }
}
