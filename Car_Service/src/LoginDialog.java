import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginDialog extends JDialog {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton LoginButton;
    private boolean loginSuccess;
    private User loggedUser;
    private Map<String, User> users;

    public LoginDialog(Frame rodič) {
        super(rodič, "Přihlášení", true);

        users = new HashMap<>();
        users.put("Dejvik", new User("Dejvik", "123", Role.ADMIN));
        users.put("mechanik", new User("mechanik", "mechanik123", Role.MECHANIK));
        users.put("zakaznik", new User("zakaznik", "zakaznik123", Role.ZAKAZNIK));
        users.put("recepcni", new User("recepcni", "recepcni123", Role.RECEPCNI));

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Uživatelské jméno:"));
        userNameField = new JTextField();
        panel.add(userNameField);
        panel.add(new JLabel("Heslo:"));
        passwordField = new JPasswordField();
        panel.add(passwordField)    ;

        LoginButton = new JButton("Přihlásit se");
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uživatelskéJméno = userNameField.getText();
                String heslo = new String(passwordField.getPassword());
                User user = users.get(uživatelskéJméno);

                if (user != null && user.getHeslo().equals(heslo)) {
                    loggedUser = user;
                    loginSuccess = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Nesprávné uživatelské jméno nebo heslo", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        add(LoginButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(rodič);
        setVisible(true);
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
