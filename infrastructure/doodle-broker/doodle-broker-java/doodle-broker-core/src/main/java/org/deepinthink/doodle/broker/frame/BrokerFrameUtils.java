/*
 * Copyright (c) 2020-2024 DeepInThink. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deepinthink.doodle.broker.frame;

import doodle.broker.frame.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

@UtilityClass
public final class BrokerFrameUtils {

  public static BrokerFrame setup(Map<String, Set<String>> tags) {
    return setup(tags, java.util.UUID.randomUUID().toString());
  }

  public static BrokerFrame setup(Map<String, Set<String>> tags, String routeId) {
    RouteSetup.Builder setup =
        RouteSetup.newBuilder()
            .setRouteId(UUID.newBuilder().setId(routeId).build())
            .setTags(toTags(tags));
    return BrokerFrame.newBuilder().setSetup(setup).build();
  }

  public static BrokerFrame unicast(Map<String, Set<String>> tags) {
    return unicast(tags, QueryType.AND);
  }

  public static BrokerFrame unicast(Map<String, Set<String>> tags, QueryType queryType) {
    return address(tags, queryType, RoutingType.UNICAST);
  }

  public static BrokerFrame multicast(Map<String, Set<String>> tags) {
    return multicast(tags, QueryType.AND);
  }

  public static BrokerFrame multicast(Map<String, Set<String>> tags, QueryType queryType) {
    return address(tags, queryType, RoutingType.MULTICAST);
  }

  public static BrokerFrame address(
      Map<String, Set<String>> tags, QueryType queryType, RoutingType routingType) {
    Address.Builder address =
        Address.newBuilder()
            .setQueryType(queryType)
            .setRoutingType(routingType)
            .setTags(toTags(tags));
    return BrokerFrame.newBuilder().setAddress(address).build();
  }

  private static Tags toTags(Map<String, Set<String>> tags) {
    Tags.Builder builder = Tags.newBuilder();
    if (CollectionUtils.isEmpty(tags)) {
      return builder.build();
    }

    for (Map.Entry<String, Set<String>> entry : tags.entrySet()) {
      builder.putTag(entry.getKey(), TagValue.newBuilder().addAllValue(entry.getValue()).build());
    }
    return builder.build();
  }

  public static Map<String, Set<String>> toMap(Tags tags) {
    if (tags == null || CollectionUtils.isEmpty(tags.getTagMap())) {
      return Collections.emptyMap();
    }

    return tags.getTagMap().entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, entry -> new HashSet<>(entry.getValue().getValueList())));
  }
}
