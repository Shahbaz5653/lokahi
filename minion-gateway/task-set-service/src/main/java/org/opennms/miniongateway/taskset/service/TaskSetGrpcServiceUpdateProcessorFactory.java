package org.opennms.miniongateway.taskset.service;

import org.opennms.taskset.service.contract.UpdateTasksRequest;
import org.springframework.stereotype.Component;

/**
 * Process task set updates in the TaskSetGrpcService.
 */
@Component
public class TaskSetGrpcServiceUpdateProcessorFactory  {

    public TaskSetGrpcServiceUpdateProcessor create(String tenantId, UpdateTasksRequest updateTasksRequest) {
        return new TaskSetGrpcServiceUpdateProcessor(tenantId, updateTasksRequest);
    }
}