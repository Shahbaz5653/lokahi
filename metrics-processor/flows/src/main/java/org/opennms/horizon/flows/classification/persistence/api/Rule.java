/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2017-2023 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2023 The OpenNMS Group, Inc.
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

package org.opennms.horizon.flows.classification.persistence.api;

import com.google.common.base.MoreObjects;

/**
 * A rule defines how a flow should be mapped.
 * From each rule a classifier is created, which allows to classify a flow by this rule.
 *
 * @author mvrueden
 */

public class Rule implements RuleDefinition {

    public static final int MIN_PORT_VALUE = 0;
    public static final int MAX_PORT_VALUE = 65535;

    public boolean canBeReversed() {
        return isOmnidirectional() &&
               (hasSrcPortDefinition() || hasSrcAddressDefinition() || hasDstPortDefinition() || hasDstAddressDefinition());
    }

    private Integer id;

    /**
     * The name to map to.
     * Must not be null.
     */
    private String name;

    /**
     * The ip address to map.
     * May contain wildcards, e.g. 192.168.1.*. 192.168.*.*.
     * May be null.
     */
    private String dstAddress;

    /**
     * The port to map.
     * May define ranges, e.g.
     * 80,8980,8000-9000
     * Must always be provided.
     */
    private String dstPort;

    // see dstPort
    private String srcPort;

    // see dstAddress
    private String srcAddress;

    private String exporterFilter;

    /**
     * The protocol to map.
     * May contain multiple values,e.g. 2,7,17 or tcp,udp
     */
    private String protocol;

    /**
     * The position of the rule within it's group.
     * Global order must consider group.position as well.
     * See {@link RulePositionComparator}.
     */
    private int position;

    private boolean omnidirectional;

    public Rule() {
        
    }

    public Rule(String name, String dstAddress, String dstPort) {
        this.name = name;
        this.dstPort = dstPort;
        this.dstAddress = dstAddress;
    }

    public Rule(String name, String port) {
        this(name, null, port);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDstAddress() {
        return dstAddress;
    }

    public void setDstAddress(String dstAddress) {
        this.dstAddress = dstAddress;
    }

    @Override
    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    @Override
    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    @Override
    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isOmnidirectional() {
        return this.omnidirectional;
    }

    public void setOmnidirectional(final boolean omnidirectional) {
        this.omnidirectional = omnidirectional;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String getExporterFilter() {
        return exporterFilter;
    }

    public void setExporterFilter(String exporterFilter) {
        this.exporterFilter = exporterFilter;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
            .add("name", name)
            .add("dstAddress", dstAddress)
            .add("dstPort", dstPort)
            .add("srcAddress", srcAddress)
            .add("srcPort", srcPort)
            .add("exporterFilter", exporterFilter)
            .add("protocol", protocol)
            .add("omnidirectional", omnidirectional)
            .add("position", position)
            .toString();
    }
}
