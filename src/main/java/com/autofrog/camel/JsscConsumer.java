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
import org.apache.camel.*;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.EventDrivenPollingConsumer;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.camel.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The HelloWorld consumer.
 */
public class JsscConsumer extends DefaultConsumer {
    private static final Logger log = LoggerFactory.getLogger(JsscConsumer.class);

    public JsscConsumer(JsscEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        log.info("Created");
    }

    @Override
    protected void doStart() throws Exception {
        log.info("Starting");
        final JsscEndpoint endpoint = (JsscEndpoint) getEndpoint();
        final SerialPort sp = endpoint.getSp();

        log.info("Adding a serial port event listener");
        sp.addEventListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent event) {
                log.info("Serial event");


                if (event.isRXCHAR() && event.getEventValue() > 0) {

                    Exchange exchange = getEndpoint().createExchange();
                    try {
                        byte[] bytes = sp.readBytes();

//                        System.out.print(String.format("Read %d bytes: ", bytes.length));
//                        for (byte b : bytes) {
//                            System.out.print(String.format("%02X ", b));
//                        }
//                        System.out.println();


                        exchange.getIn().setBody(bytes);
                        log.info("Sending message onward");
                        getProcessor().process(exchange);
                    } catch (Exception e) {
                        log.info("Exception processing message",e );
                            exchange.setException(e);
                    }
                }
            }
        });
        log.info("Listener added, everything appears to be started now.");

    }

}
