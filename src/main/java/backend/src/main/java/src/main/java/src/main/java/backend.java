
package backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkCSVExporter {

    private BenchmarkCSVExporter() {
        // Prevent object creation.
    }

    public static void exportResults(
            String fileName, String csvContent) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            writer.write(csvContent);

            System.out.println(
                    "\nCSV exported successfully: " + fileName
            );

        } catch (IOException e) {

            System.out.println(
                    "Error exporting CSV: " + e.getMessage()
            );
        }
    }
}
