package main;

import java.awt.*;

public class DialogBox {
    GamePanel gp;
    private String dialogText = "Hello, this is a dialog!";

    public DialogBox (GamePanel gp) {
        this.gp = gp;
    }

    public void drawDialogBox (Graphics2D g2) {
        // Draw the dialog box background
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(50, 100, 300, 60);

        // Draw the border of the dialog box
        g2.setColor(Color.BLACK);
        g2.drawRect(50, 100, 300, 60);

        // Draw the text inside the dialog box
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString(dialogText, 60, 130); // Adjust coordinates as needed
    }
}
