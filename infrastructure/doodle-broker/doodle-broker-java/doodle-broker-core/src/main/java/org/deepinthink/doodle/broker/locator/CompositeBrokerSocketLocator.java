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

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class CompositeBrokerSocketLocator<SOCKET> implements BrokerSocketLocator<SOCKET> {
  List<BrokerSocketLocator<SOCKET>> locators;

  @Override
  public boolean supports(RoutingType routingType) {
    for (BrokerSocketLocator<SOCKET> locator : locators) {
      if (locator.supports(routingType)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public SOCKET locate(SOCKET socket, Address address) {
    for (BrokerSocketLocator<SOCKET> locator : locators) {
      if (locator.supports(address.getRoutingType())) {
        return locator.locate(socket, address);
      }
    }
    throw new IllegalStateException(
        "Can not find SocketLocator for RoutingType: " + address.getRoutingType());
  }
}
