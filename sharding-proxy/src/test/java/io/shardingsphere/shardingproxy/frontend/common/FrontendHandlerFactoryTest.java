/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
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
 * </p>
 */

package io.shardingsphere.shardingproxy.frontend.common;

import io.shardingsphere.core.constant.DatabaseType;
import io.shardingsphere.core.constant.properties.ShardingProperties;
import io.shardingsphere.shardingproxy.frontend.mysql.MySQLFrontendHandler;
import io.shardingsphere.shardingproxy.runtime.GlobalRegistry;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public final class FrontendHandlerFactoryTest {
    
    @BeforeClass
    @SneakyThrows
    public static void beforeClass() {
        Field field = GlobalRegistry.getInstance().getClass().getDeclaredField("shardingProperties");
        field.setAccessible(true);
        field.set(GlobalRegistry.getInstance(), new ShardingProperties(new Properties()));
    }
    
    @Test
    public void assertCreateFrontendHandlerInstanceWithMySQL() {
        assertThat(FrontendHandlerFactory.createFrontendHandlerInstance(DatabaseType.MySQL, null), instanceOf(MySQLFrontendHandler.class));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void assertCreateFrontendHandlerInstanceWhenUnsupported() {
        FrontendHandlerFactory.createFrontendHandlerInstance(DatabaseType.Oracle, null);
    }
}
