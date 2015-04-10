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
import jssc.SerialPortException;
import org.apache.camel.*;
import org.apache.camel.api.management.ManagedAttribute;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.UriComponentConfiguration;
import org.apache.camel.impl.UriEndpointComponent;
import org.apache.camel.spi.*;
import org.apache.camel.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final static Logger log = LoggerFactory.getLogger(JsscEndpoint.class);

    @UriParam
    @Metadata(required = "true")
    private int baud;


    private String path;

    public JsscEndpoint(String endpointUri, Component component) {
        super(endpointUri, component);



    }


    public int getBaud() {
        return baud;
    }

    public void setBaud(int baud) {
        this.baud = baud;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private SerialPort sp = null;





    public Producer createProducer() throws Exception {
        log.info("Please create a producer");
        return new JsscProducer(this, getSerialPort());
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        log.info("Please create a consumer");
        return new JsscConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }


    private SerialPort getSerialPort() throws SerialPortException {
        if(sp == null) {
            log.info("Creating serial port {}", path);
            sp = new SerialPort(path);

            log.info("Opening serial port");
            sp.openPort();

            log.info("Setting serial port configuration {}", baud);
            sp.setParams(baud, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE );
        }

        log.info("Returning serial port");
        return sp;
    }

    public SerialPort getSp() throws SerialPortException {
        return getSerialPort();
    }
}