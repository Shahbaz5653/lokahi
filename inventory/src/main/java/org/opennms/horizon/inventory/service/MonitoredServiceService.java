package org.opennms.horizon.inventory.service;

import lombok.RequiredArgsConstructor;
import org.opennms.horizon.inventory.dto.MonitoredServiceDTO;
import org.opennms.horizon.inventory.mapper.MonitoredServiceMapper;
import org.opennms.horizon.inventory.model.MonitoredService;
import org.opennms.horizon.inventory.repository.MonitoredServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonitoredServiceService {
    private final MonitoredServiceRepository modelRepo;

    private final MonitoredServiceMapper mapper;

    public List<MonitoredServiceDTO> findByTenantId(String tenantId) {
        List<MonitoredService> all = modelRepo.findByTenantId(tenantId);
        return all
            .stream()
            .map(mapper::modelToDTO)
            .collect(Collectors.toList());
    }
}
