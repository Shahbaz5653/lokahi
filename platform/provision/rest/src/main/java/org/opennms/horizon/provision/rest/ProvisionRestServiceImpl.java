package org.opennms.horizon.provision.rest;

import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.opennms.netmgt.provision.service.Provisioner;

@AllArgsConstructor
public class ProvisionRestServiceImpl implements ProvisionRestService {
    Provisioner provisioner;

    @Override
    public Response publishRequisition(String requisition) {
        return null;
    }
}
