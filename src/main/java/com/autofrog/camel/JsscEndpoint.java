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

import jssc.SerialPort;
import org.apache.camel.*;
import org.apache.camel.api.management.ManagedAttribute;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.UriComponentConfiguration;
import org.apache.camel.impl.UriEndpointComponent;
import org.apache.camel.spi.*;
import org.apache.camel.util.URISupport;

import java.util.Map;

/**
 * Represents a HelloWorld endpoint.
 */
@ManagedResource
@UriEndpoint(scheme = "jssc",
        syntax = "jssc:path",
        consumerClass = JsscConsumer.class,
        title = "JSSC Component")
public class JsscEndpoint extends DefaultEndpoint {

    @UriParam
    private JsscConfiguration config;

    private SerialPort sp = null;

    public JsscEndpoint(String endpointUri, Component component, JsscConfiguration config) {
        super(endpointUri, component);
        this.config = config;
    }

    public Producer createProducer() throws Exception {
        return new JsscProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new JsscConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }


}