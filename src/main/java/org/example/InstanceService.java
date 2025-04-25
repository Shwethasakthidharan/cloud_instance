package org.example;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstanceService {

    private final List<Instance> instances;

    public InstanceService(CsvProcessor csvProcessor) {
        System.out.println("Initializing InstanceService...");
        this.instances = csvProcessor.loadInstances();
        System.out.println("InstanceService initialized with " + this.instances.size() + " instances");
    }

    public List<Instance> getAllInstances() {
        return instances;
    }
}