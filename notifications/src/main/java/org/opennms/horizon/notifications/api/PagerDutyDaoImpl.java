/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2022 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2022 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.horizon.notifications.api;

import java.util.List;

import org.opennms.horizon.notifications.api.dto.PagerDutyConfigDTO;
import org.opennms.horizon.notifications.exceptions.NotificationConfigUninitializedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagerDutyDaoImpl implements PagerDutyDao{
    private static final Logger LOG = LoggerFactory.getLogger(PagerDutyDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PagerDutyConfigDTO getConfig() throws NotificationConfigUninitializedException {
        String sql = "SELECT token, integrationKey FROM pager_duty_config";
        List<PagerDutyConfigDTO> configList = null;
        try {
            configList = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                    new PagerDutyConfigDTO(
                        rs.getString("token"),
                        rs.getString("integrationKey")
                    )
            );
        } catch (BadSqlGrammarException e) {
            throw new NotificationConfigUninitializedException("PagerDuty config not initialized. Table does not exist.", e);
        }

        if (configList.size() != 1) {
            throw new NotificationConfigUninitializedException("PagerDuty config not initialized. Row count=" + configList.size());
        }

        return configList.get(0);
    }

    @Override
    public void saveConfig(PagerDutyConfigDTO config) {
        int count = getRowCount();

        if (count == 0) {
            jdbcTemplate.update("INSERT INTO pager_duty_config(token, integrationkey) VALUES(?,?)", config.getToken(), config.getIntegrationkey());
        } else {
            jdbcTemplate.update("UPDATE pager_duty_config SET token=?, integrationkey=?", config.getToken(), config.getIntegrationkey());
        }
    }

    private int getRowCount() {
        String sql = "SELECT count(*) FROM pager_duty_config";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}