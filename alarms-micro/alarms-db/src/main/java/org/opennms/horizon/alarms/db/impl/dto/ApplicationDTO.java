/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
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

package org.opennms.horizon.alarms.db.impl.dto;

import com.google.common.base.MoreObjects;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * An Application is a grouping of services that belong together.
 * They can run in different locations.
 * An example would be "website", or "database".
 */
@Entity
@Table(name = "applications")
@Getter
@Setter
public class ApplicationDTO implements Comparable<ApplicationDTO> {

    @Id
    @Column(nullable=false)
    @SequenceGenerator(name = "opennmsSequence", sequenceName = "opennmsNxtId", allocationSize = 1)
    @GeneratedValue(generator = "opennmsSequence")
    private Integer id;

    @Column(length=32, nullable=false, unique=true)
    private String name;

    @ManyToMany(
        mappedBy="applications",
        cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<MonitoredServiceDTO> monitoredServices = new LinkedHashSet<>();

    /**
     * These are locations from where the application is monitored.
     */
    @ManyToMany( cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="application_perspective_location_map",
        joinColumns=@JoinColumn(name="appid", referencedColumnName = "id"),
        inverseJoinColumns=@JoinColumn(name="monitoringlocationid", referencedColumnName = "id"))
    private Set<MonitoringLocationDTO> perspectiveLocations = new LinkedHashSet<>();

    public void setMonitoredServices(Set<MonitoredServiceDTO> services) {
        monitoredServices = services;
    }

    public void addMonitoredService(MonitoredServiceDTO service) {
        getMonitoredServices().add(service);
    }

    public void removeMonitoredService(MonitoredServiceDTO service) {
        getMonitoredServices().remove(service);
    }

    public void addPerspectiveLocation(MonitoringLocationDTO perspectiveLocation) {
        getPerspectiveLocations().add(perspectiveLocation);
    }

    @Override
    public int compareTo(ApplicationDTO o) {
        return getName().compareTo(o.getName());
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
        .add("id", getId())
        .add("name", getName())
        .toString();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ApplicationDTO) {
            ApplicationDTO app = (ApplicationDTO)obj;
            return getName().equals(app.getName());
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}
