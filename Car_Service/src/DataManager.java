import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    // Dynamicky získá aktuální složku projektu
    private static final String BASE_PATH = Paths.get(System.getProperty("user.dir")).toAbsolutePath().toString();
    private static final String CUSTOMERS_FILE = Paths.get(BASE_PATH, "customers.txt").toString();
    private static final String VEHICLES_FILE = Paths.get(BASE_PATH, "vehicles.txt").toString();
    private static final String SERVICES_FILE = Paths.get(BASE_PATH, "services.txt").toString();

    public static void saveCustomers(List<String> customers) {
        saveToFile(CUSTOMERS_FILE, customers);
    }

    public static List<String> loadCustomers() {
        return loadFromFile(CUSTOMERS_FILE);
    }

    public static void saveVehicles(List<String> vehicles) {
        saveToFile(VEHICLES_FILE, vehicles);
    }

    public static List<String> loadVehicles() {
        return loadFromFile(VEHICLES_FILE);
    }

    public static void saveServices(List<String> services) {
        saveToFile(SERVICES_FILE, services);
    }

    public static List<String> loadServices() {
        return loadFromFile(SERVICES_FILE);
    }

    private static void saveToFile(String filename, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String entry : data) {
                writer.write(entry);
                writer.newLine();
            }
            System.out.println("✅ Data saved to " + filename + ": " + data);
        } catch (IOException e) {
            System.err.println("❌ Error saving data to file: " + filename);
            e.printStackTrace();
        }
    }

    private static List<String> loadFromFile(String filename) {
        File file = new File(filename);
        List<String> data = new ArrayList<>();

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("📄 Created new file: " + filename);
            } catch (IOException e) {
                System.err.println("❌ Error creating file: " + filename);
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            System.out.println("📄 Data loaded from " + filename + ": " + data);
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + filename);
            e.printStackTrace();
        }
        return data;
    }
}
