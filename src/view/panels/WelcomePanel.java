package view.panels;

import main.App;
import javax.swing.*;
import static main.App.padding;
import static view.UserInterfaceUtils.createButton;
import static view.UserInterfaceUtils.createLabelH1;

public class WelcomePanel extends JPanel {
    public WelcomePanel(App app) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        add(createLabelH1("Welcome to Daily"));

        add(createButton("Sign up", e -> app.showPanel("signup")));
        add(createButton("Log in", e -> app.showPanel("login")));
    }
}
