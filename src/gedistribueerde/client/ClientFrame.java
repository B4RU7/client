package gedistribueerde.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class ClientFrame extends JFrame implements TextListener {
    private JTextArea textArea;
    private Document document;

    public ClientFrame(Document document) throws HeadlessException {
        this.document = document;
        setTitle("DocumentViewer");
        createComponents();
        layoutComponents();
        addListeners();
        setSize(300, 300);
        setVisible(true);
    }

    private void createComponents() {
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
    }

    private void layoutComponents() {
        JScrollPane textPane = new JScrollPane(textArea);
        Container contentPane = getContentPane();
        contentPane.add(textPane, BorderLayout.CENTER);
    }

    private void addListeners() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void textValueChanged(TextEvent textEvent) {
        String text = document.getText();
        this.textArea.setText(text);
    }
}
