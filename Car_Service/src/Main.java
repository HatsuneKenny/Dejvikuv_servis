import javax.swing.SwingUtilities;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            if (login.isLoginSuccess()) {
                new AutoServiceApp(login.getLoggedUser());
            }
        });
    }
}
