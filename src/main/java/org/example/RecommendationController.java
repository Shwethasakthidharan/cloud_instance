package org.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    private final InstanceService instanceService;

    public RecommendationController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Instance>> getRecommendations(
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String os,
            @RequestParam(required = false) Double vcpu,
            @RequestParam(required = false) Double memory,
            @RequestParam(required = false) String storage,
            @RequestParam(required = false) String marketOption,
            @RequestParam(required = false) Double maxPrice) {

        List<Instance> allInstances = instanceService.getAllInstances();

        List<Instance> filtered = allInstances.stream()
                .filter(instance -> provider == null || instance.getProvider().equalsIgnoreCase(provider))
                .filter(instance -> location == null || instance.getLocation().equalsIgnoreCase(location))
                .filter(instance -> os == null || instance.getOperatingSystem().equalsIgnoreCase(os))
                .filter(instance -> vcpu == null || instance.getVcpu() == vcpu)
                .filter(instance -> memory == null || instance.getMemory() == memory)
                .filter(instance -> storage == null || instance.getStorage().equalsIgnoreCase(storage))
                .filter(instance -> marketOption == null || instance.getMarketOption().equalsIgnoreCase(marketOption))
                .filter(instance -> maxPrice == null || instance.getPrice() <= maxPrice)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtered.isEmpty() ? allInstances : filtered);
    }
}
