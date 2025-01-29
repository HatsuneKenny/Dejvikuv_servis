import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Locale;

public class AutoServiceApp extends JFrame {
    private User loggedUser;

    public AutoServiceApp(User loggedUser) {
        this.loggedUser = loggedUser;
        setTitle(Messages.get("title"));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Přidáme vlastní obsluhu zavírání
        setLocationRelativeTo(null);

        // Přidání posluchače na událost zavírání
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmAndExit(); // Zavolání potvrzovací metody
            }
        });

        createMenu();
        setVisible(true);
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu languageMenu = new JMenu("Language");
        JMenuItem english = new JMenuItem("English");
        JMenuItem czech = new JMenuItem("Čeština");

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Messages.setLocale(new Locale("en", "US"));
                reload();
            }
        });

        czech.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Messages.setLocale(new Locale("cs", "CZ"));
                reload();
            }
        });

        languageMenu.add(english);
        languageMenu.add(czech);
        menuBar.add(languageMenu);

        if (loggedUser != null && (loggedUser.getRole() == Role.ADMIN || loggedUser.getRole() == Role.MECHANIK || loggedUser.getRole() == Role.RECEPCNI)) {
            JMenu customerMenu = new JMenu(Messages.get("menu.customers"));
            JMenuItem addCustomer = new JMenuItem(Messages.get("menu.addCustomer"));
            JMenuItem viewCustomers = new JMenuItem(Messages.get("menu.viewCustomers"));

            addCustomer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddCustomerDialog(AutoServiceApp.this);
                }
            });

            viewCustomers.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewCustomersDialog(AutoServiceApp.this);
                }
            });

            customerMenu.add(addCustomer);
            customerMenu.add(viewCustomers);
            menuBar.add(customerMenu);
        }

        if (loggedUser != null && (loggedUser.getRole() == Role.ADMIN || loggedUser.getRole() == Role.MECHANIK)) {
            JMenu vehicleMenu = new JMenu(Messages.get("menu.vehicles"));
            JMenuItem addVehicle = new JMenuItem(Messages.get("menu.addVehicle"));
            JMenuItem viewVehicles = new JMenuItem(Messages.get("menu.viewVehicles"));

            addVehicle.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddVehicleDialog(AutoServiceApp.this);
                }
            });

            viewVehicles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewVehiclesDialog(AutoServiceApp.this);
                }
            });

            vehicleMenu.add(addVehicle);
            vehicleMenu.add(viewVehicles);
            menuBar.add(vehicleMenu);
        }

        if (loggedUser != null && (loggedUser.getRole() == Role.ADMIN || loggedUser.getRole() == Role.MECHANIK)) {
            JMenu serviceMenu = new JMenu(Messages.get("menu.services"));
            JMenuItem addService = new JMenuItem(Messages.get("menu.addService"));
            JMenuItem viewServices = new JMenuItem(Messages.get("menu.viewServices"));

            addService.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddServiceDialog(AutoServiceApp.this);
                }
            });

            viewServices.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewServicesDialog(AutoServiceApp.this);
                }
            });

            serviceMenu.add(addService);
            serviceMenu.add(viewServices);
            menuBar.add(serviceMenu);
        }

        // Přidání menu pro nastavení vzhledu
        JMenu appearanceMenu = new JMenu(Messages.get("menu.appearance"));
        JMenuItem lightTheme = new JMenuItem(Messages.get("menu.lightTheme"));
        JMenuItem darkTheme = new JMenuItem(Messages.get("menu.darkTheme"));

        lightTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                reload();
            }
        });
        darkTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                reload();
            }
        });

        JMenuItem windowsTheme = new JMenuItem("Windows Theme");
        windowsTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                reload();
            }
        });
        appearanceMenu.add(windowsTheme);
        appearanceMenu.add(lightTheme);
        appearanceMenu.add(darkTheme);
        menuBar.add(appearanceMenu);

        // Přidání tlačítka pro odhlášení
        if (loggedUser != null) {
            JMenu userMenu = new JMenu(loggedUser.getUserName());
            JMenuItem logout = new JMenuItem(Messages.get("button.logout"));
            logout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logout();
                }
            });

            userMenu.add(logout);
            menuBar.add(userMenu);
        }

        setJMenuBar(menuBar);
    }

    private void setLookAndFeel(String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reload() {
        this.dispose();
        new AutoServiceApp(loggedUser);
    }

    private void logout() {
        this.loggedUser = null; // Resetování přihlášeného uživatele
        this.dispose(); // Zavření aktuálního okna
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            if (login.isLoginSuccess()) {
                new AutoServiceApp(login.getLoggedUser()); // Předání nového přihlášeného uživatele
            }
        });
    }

    private void confirmAndExit() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                Messages.get("dialog.exitConfirmation.message"), // Text zprávy
                Messages.get("dialog.exitConfirmation.title"), // Titulek dialogu
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0); // Ukončí aplikaci
        }
    }

}