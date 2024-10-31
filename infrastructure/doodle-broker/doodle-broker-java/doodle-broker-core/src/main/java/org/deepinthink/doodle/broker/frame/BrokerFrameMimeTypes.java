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

import lombok.experimental.UtilityClass;
import org.springframework.util.MimeType;

@UtilityClass
public final class BrokerFrameMimeTypes {
  public static final MimeType BROKER_FRAME_MIME_TYPE =
      new MimeType("message", "doodle.broker.frame.v0");

  public static String BROKER_FRAME_METADATA_KEY = "brokerFrame";
}
