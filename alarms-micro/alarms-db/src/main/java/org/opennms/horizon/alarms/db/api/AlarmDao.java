package org.opennms.horizon.alarms.db.api;

import org.opennms.horizon.alarms.db.impl.dto.AlarmDTO;

public interface AlarmDao extends BasicDao<AlarmDTO, Integer> {
    AlarmDTO findByReductionKey(String reductionKey);
}
