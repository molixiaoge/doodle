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
package org.deepinthink.doodle.broker.query;

import doodle.broker.frame.QueryType;
import doodle.broker.frame.Tags;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class CompositedBrokerSocketQuery<SOCKET> implements BrokerSocketQuery<SOCKET> {
  List<BrokerSocketQuery<SOCKET>> queries;

  @Override
  public List<SOCKET> query(SOCKET socket, Tags tags, QueryType queryType) {
    if (tags == null || CollectionUtils.isEmpty(tags.getTagMap())) {
      throw new IllegalArgumentException("tags cannot not be empty!");
    }

    List<SOCKET> result = new ArrayList<>();
    for (BrokerSocketQuery<SOCKET> query : queries) {
      if (query.supports(socket)) {
        List<SOCKET> found = query.query(socket, tags, queryType);
        if (!CollectionUtils.isEmpty(found)) {
          result.addAll(found);
        }
      }
    }
    return result;
  }
}
