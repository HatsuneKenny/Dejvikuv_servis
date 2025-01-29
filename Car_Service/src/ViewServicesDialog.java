import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewServicesDialog extends JDialog {
    private JList<String> serviceList;
    private DefaultListModel<String> listModel;
    private JTextField searchField;

    public ViewServicesDialog(JFrame parent) {
        super(parent, Messages.get("dialog.viewServices.title"), true);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton(Messages.get("button.search"));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterServices();
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        List<String> services = DataManager.loadServices();
        for (String service : services) {
            listModel.addElement(formatService(service));
        }

        serviceList = new JList<>(listModel);
        add(new JScrollPane(serviceList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton(Messages.get("button.edit"));
        JButton deleteButton = new JButton(Messages.get("button.delete"));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = serviceList.getSelectedIndex();
                if (selectedIndex != -1) {
                    new EditServiceDialog(parent, selectedIndex);
                    reloadServices();
                } else {
                    JOptionPane.showMessageDialog(ViewServicesDialog.this, Messages.get("error.selectItemToEdit"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = serviceList.getSelectedIndex();
                if (selectedIndex != -1) {
                    int confirm = JOptionPane.showConfirmDialog(ViewServicesDialog.this, Messages.get("confirm.delete"), Messages.get("button.confirmation"), JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteService(selectedIndex);
                        reloadServices();
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewServicesDialog.this, Messages.get("error.selectItemToDelete"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void filterServices() {
        String searchText = searchField.getText().toLowerCase();
        listModel.clear();
        List<String> services = DataManager.loadServices();
        for (String service : services) {
            if (service.toLowerCase().contains(searchText)) {
                listModel.addElement(formatService(service));
            }
        }
    }

    private void reloadServices() {
        listModel.clear();
        List<String> services = DataManager.loadServices();
        for (String service : services) {
            listModel.addElement(formatService(service));
        }
    }

    private void deleteService(int index) {
        List<String> services = DataManager.loadServices();
        services.remove(index);
        DataManager.saveServices(services);
    }

    private String formatService(String service) {
        String[] serviceData = service.split(",");
        return String.format("%s, %s, %s, %s, %s", serviceData[0], serviceData[1], serviceData[2], serviceData[3], serviceData[4]);
    }
}
