package ui.elements;

import javax.swing.*;
import java.awt.*;

public class Dialog {
    public static void showWarning(String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
        customizeOptionPane(pane);
        JDialog dialog = pane.createDialog(null, title);
        dialog.setVisible(true);
    }

    public static void showMessage(String message, String title) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
        customizeOptionPane(pane);
        JDialog dialog = pane.createDialog(null, title);
        dialog.setVisible(true);
    }

    public static int showConfirm(String message, String title) {
        JOptionPane pane = new JOptionPane(
                message,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION
        );
        customizeOptionPane(pane);

        JDialog dialog = pane.createDialog(null, title);
        dialog.setVisible(true);

        Object value = pane.getValue();
        return (value instanceof Integer) ? (Integer) value : JOptionPane.CLOSED_OPTION;
    }

    public static int showConfirmPanel(JPanel panel, String title) {
        JOptionPane optionPane = new JOptionPane(
                panel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION
        );
        customizeOptionPane(optionPane);
        JDialog dialog = optionPane.createDialog(null, title);
        dialog.setVisible(true);

        Object value = optionPane.getValue();
        return (value != null) ? (int) value : JOptionPane.CLOSED_OPTION;
    }

    public static String showInput(String message, String initialValue, String title) {
        String input = (String) JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                initialValue
        );
        return input;
    }

    private static void customizeOptionPane(JOptionPane pane) {
        UIManager.put("OptionPane.messageFont", new Font("Lucida Grande", Font.PLAIN, 15));
        UIManager.put("OptionPane.buttonFont", new Font("Lucida Grande", Font.PLAIN, 12));
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
    }
}