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

package org.opennms.horizon.server.mapper.discovery;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.opennms.horizon.inventory.dto.AzureActiveDiscoveryCreateDTO;
import org.opennms.horizon.inventory.dto.AzureActiveDiscoveryDTO;
import org.opennms.horizon.server.mapper.TagMapper;
import org.opennms.horizon.server.model.inventory.discovery.active.AzureActiveDiscovery;
import org.opennms.horizon.server.model.inventory.discovery.active.AzureActiveDiscoveryCreate;


@Mapper(componentModel = "spring", uses = {TagMapper.class},
    // Needed for grpc proto mapping
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface AzureActiveDiscoveryMapper {

    AzureActiveDiscovery dtoToAzureActiveDiscovery(AzureActiveDiscoveryDTO dto);

    @Mapping(target = "location", source = "location", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "tagsList", source = "tags", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AzureActiveDiscoveryCreateDTO azureDiscoveryCreateToProto(AzureActiveDiscoveryCreate request);
}