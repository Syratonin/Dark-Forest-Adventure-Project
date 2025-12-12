import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdventureGUI {

    private JFrame frame;
    private JTextArea storyOut;
    private JTextField inputField;
    private JButton choiceAButton;
    private JButton choiceBButton;
    private JButton nextButton;

    public AdventureGUI() {
        frame = new JFrame("Adventure Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        storyOut = new JTextArea();
        storyOut.setEditable(false);
        storyOut.setLineWrap(true);
        storyOut.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(storyOut);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(200, 30));

        choiceAButton = new JButton("A");
        choiceBButton = new JButton("B");
        nextButton = new JButton("Next");

        JPanel buttonPanel = new JPanel();
        // buttonPanel.add(choiceAButton); made for future updates, not using right now
        // buttonPanel.add(choiceBButton);
        buttonPanel.add(nextButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER); // input field above
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH); // buttons below

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // GUI Setters
    public void appendStory(String text) {
        storyOut.append(text + "\n");
        storyOut.setCaretPosition(storyOut.getDocument().getLength());
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void clearInput() {
        inputField.setText("");
    }

    public void setChoiceAListener(ActionListener listener) {
        choiceAButton.addActionListener(listener);
    }

    public void setChoiceBListener(ActionListener listener) {
        choiceBButton.addActionListener(listener);
    }

    public void setNextListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }
}
