package app.View;

import javax.swing.*;


public class EditorView extends JFrame {

    public EditorView() {
        super("STE");

        JButton save = new JButton("Save");

        JTextArea textArea = new JTextArea(50, 150);

        JPanel mainPanel = new JPanel();
        JPanel menuPanel = new JPanel();


        getContentPane().add(menuPanel);
        getContentPane().add(mainPanel);
        mainPanel.add(textArea);
        pack();
        setVisible(true);
    }
}
