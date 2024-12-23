package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInputBox extends JPanel implements KeyListener {
    private StringBuilder username = new StringBuilder();
    private boolean isInputActive = true;

    public GameInputBox() {
        JFrame frame = new JFrame("Game Username Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw input box
        g2.setColor(Color.WHITE);
        g2.drawRect(300, 250, 200, 50);
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        // Draw the username
        g2.drawString(username.toString(), 310, 280);

        // Draw instructions
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Enter your username and press Enter", 270, 330);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (isInputActive) {
            char keyChar = e.getKeyChar();

            if (Character.isLetterOrDigit(keyChar)) {
                username.append(keyChar);
            } else if (keyChar == '\b' && username.length() > 0) { // Handle backspace
                username.deleteCharAt(username.length() - 1);
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && isInputActive) {
            System.out.println("Username entered: " + username);
            JOptionPane.showMessageDialog(this, "Welcome, " + username + "!");
            isInputActive = false; // Disable input after submission
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new GameInputBox();
    }
}
