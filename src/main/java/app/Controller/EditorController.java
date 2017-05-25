package app.Controller;

import app.View.EditorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andre on 23.05.2017.
 */
public class EditorController implements Editor {
    protected EditorView view;

    public EditorController() {
        this.view = new EditorView(this);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(view);
    }
}
