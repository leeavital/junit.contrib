/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.junit.contrib.assertthrows.impl;

/**
 * Utility methods for proxy classes and invocation handlers.
 *
 * @author Thomas Mueller
 */
public class ProxyUtils {

    private ProxyUtils() {
        // utility class
    }

    /**
     * Get the default value for the given class. For non-primitive classes,
     * this is null, and for primitive classes this is the zero or false.
     *
     * @param clazz the class
     * @return false, zero, or null
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return null;
        }
        if (clazz == boolean.class) {
            return false;
        } else if (clazz == byte.class) {
            return (byte) 0;
        } else if (clazz == char.class) {
            return (char) 0;
        } else if (clazz == short.class) {
            return (short) 0;
        } else if (clazz == int.class) {
            return 0;
        } else if (clazz == long.class) {
            return 0L;
        } else if (clazz == float.class) {
            return 0F;
        } else if (clazz == double.class) {
            return 0D;
        }
        return null;
    }

}
