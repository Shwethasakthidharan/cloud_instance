package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvProcessor {

    public List<Instance> loadInstances() {
        List<Instance> instances = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource("aws_pricing_filtered.txt");
            System.out.println("Loading CSV from: " + resource.getURI());

            try (CSVReader reader = new CSVReader(new InputStreamReader(
                    resource.getInputStream(), StandardCharsets.UTF_8))) {


                String[] header = reader.readNext();
                if (header == null) {
                    throw new RuntimeException("CSV file is empty");
                }

                String[] row;
                int lineNumber = 1;
                while ((row = reader.readNext()) != null) {
                    lineNumber++;
                    try {
                        if (row.length < 9) {
                            System.err.println("Skipping incomplete row at line " + lineNumber);
                            continue;
                        }

                        Instance instance = new Instance();
                        instance.setInstanceType(cleanValue(row[1]));
                        instance.setProvider("AWS");
                        instance.setLocation(cleanValue(row[7]));
                        instance.setOperatingSystem(cleanValue(row[5]));
                        instance.setVcpu(parseDouble(row[2]));
                        instance.setMemory(parseMemory(row[3]));
                        instance.setStorage(cleanValue(row[4]));
                        instance.setMarketOption(cleanValue(row[6]));
                        instance.setPrice(parseDouble(row[8]));

                        instances.add(instance);
                    } catch (Exception e) {
                        System.err.println("Error processing line " + lineNumber + ": " + String.join(",", row));
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Failed to load instances from CSV", e);
        }

        System.out.println("Successfully loaded " + instances.size() + " instances");
        return instances;
    }

    private String cleanValue(String value) {
        return value == null ? "" : value.trim();
    }

    private double parseDouble(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("N/A")) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("Warning: Could not parse number from: " + value);
            return 0.0;
        }
    }

    private double parseMemory(String memoryValue) {
        // Remove "GiB" and any non-numeric characters
        String numericPart = memoryValue.replaceAll("[^0-9.]", "").trim();
        return parseDouble(numericPart);
    }
}

