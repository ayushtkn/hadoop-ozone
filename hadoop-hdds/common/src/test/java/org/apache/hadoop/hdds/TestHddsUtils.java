/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdds;

import java.nio.file.Paths;
import java.util.Optional;

import org.apache.hadoop.test.LambdaTestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing HddsUtils.
 */
public class TestHddsUtils {

  @Test
  public void testGetHostName() {
    Assert.assertEquals(Optional.of("localhost"),
        HddsUtils.getHostName("localhost:1234"));

    Assert.assertEquals(Optional.of("localhost"),
        HddsUtils.getHostName("localhost"));

    Assert.assertEquals(Optional.empty(),
        HddsUtils.getHostName(":1234"));
  }

  @Test
  public void validatePath() throws Exception {
    HddsUtils.validatePath(Paths.get("/"), Paths.get("/"));
    HddsUtils.validatePath(Paths.get("/a"), Paths.get("/"));
    HddsUtils.validatePath(Paths.get("/a"), Paths.get("/a"));
    HddsUtils.validatePath(Paths.get("/a/b"), Paths.get("/a"));
    HddsUtils.validatePath(Paths.get("/a/b/c"), Paths.get("/a"));
    HddsUtils.validatePath(Paths.get("/a/../a/b"), Paths.get("/a"));

    LambdaTestUtils.intercept(IllegalArgumentException.class,
        () -> HddsUtils.validatePath(Paths.get("/b/c"), Paths.get("/a")));
    LambdaTestUtils.intercept(IllegalArgumentException.class,
        () -> HddsUtils.validatePath(Paths.get("/"), Paths.get("/a")));
    LambdaTestUtils.intercept(IllegalArgumentException.class,
        () -> HddsUtils.validatePath(Paths.get("/a/.."), Paths.get("/a")));
    LambdaTestUtils.intercept(IllegalArgumentException.class,
        () -> HddsUtils.validatePath(Paths.get("/a/../b"), Paths.get("/a")));
  }

}