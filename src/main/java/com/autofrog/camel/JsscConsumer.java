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

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.EventDrivenPollingConsumer;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.camel.util.URISupport;

/**
 * The HelloWorld consumer.
 */
public class JsscConsumer extends DefaultConsumer {
    public JsscConsumer(JsscEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    @Override
    protected void doStart() throws Exception {

        final JsscEndpoint endpoint = (JsscEndpoint) getEndpoint();
        final SerialPort sp = endpoint.getSp();

        sp.addEventListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent event) {

                if (event.isRXCHAR() && event.getEventValue() > 0) {
                    Exchange exchange = endpoint.createExchange();
                    try {
                        byte [] bytes = sp.readBytes();
                        exchange.getIn().setBody("read " + bytes.length + " bytes");e
                        getProcessor().process(exchange);

                    } catch (Exception e) {
                        exchange.setException(e);
                    }
                }
            }
        });

    }
}
