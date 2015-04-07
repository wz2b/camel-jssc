/**
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
package com.autofrog.camel;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ComponentConfiguration;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.UriEndpointComponent;
import org.apache.camel.spi.UriEndpoint;

/**
 * Represents the component that manages {@link JsscEndpoint}.
 */

public class JsscComponent extends UriEndpointComponent {

    private JsscConfiguration config;

    public JsscComponent(Class<? extends Endpoint> endpointClass) {
        super(endpointClass);
    }

    public JsscComponent(CamelContext context, Class<? extends Endpoint> endpointClass) {
        super(context, endpointClass);
    }

    protected Endpoint createEndpoint(String uri,
                                      String remaining,
                                      Map<String, Object> parameters) throws Exception {

        JsscEndpoint endpoint = new JsscEndpoint(uri, this, config);
        setProperties(endpoint, parameters);
        return endpoint;
    }

    public JsscConfiguration getConfig() {
        return config;
    }

    public void setConfig(JsscConfiguration config) {
        this.config = config;
    }
}
