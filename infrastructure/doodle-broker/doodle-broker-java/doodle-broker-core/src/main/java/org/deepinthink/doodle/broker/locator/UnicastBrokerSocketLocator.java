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
package org.deepinthink.doodle.broker.locator;

import doodle.broker.frame.Address;
import doodle.broker.frame.RoutingType;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.deepinthink.doodle.broker.query.BrokerSocketQuery;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class UnicastBrokerSocketLocator<SOCKET> implements BrokerSocketLocator<SOCKET> {
  BrokerSocketQuery<SOCKET> query;

  @Override
  public boolean supports(RoutingType routingType) {
    return routingType == RoutingType.UNICAST;
  }

  @Override
  public SOCKET locate(SOCKET socket, Address address) {
    List<SOCKET> found = query.query(socket, address.getTags(), address.getQueryType());
    if (CollectionUtils.isEmpty(found)) {
      throw new IllegalArgumentException("Can not find routing destination provider!");
    }
    return found.get(0); // always find first
  }
}
