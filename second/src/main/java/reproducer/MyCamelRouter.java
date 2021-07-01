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
package reproducer;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import reproducer.MyLevelDbRepo;
import reproducer.MyStringAggregationStrategy;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MyCamelRouter extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        // here is the Camel route where we aggregate
        from("direct:start")
                .aggregate(header("id"), new MyStringAggregationStrategy())
                // use our created leveldb repo as aggregation repository
                .completionSize(10).aggregationRepository(() -> new MyLevelDbRepo())
                .to("stream:out");

        from("timer:hello?period=50&repeatCount=5")
                .setBody(exchange -> "second-" + exchange.getProperty(Exchange.TIMER_COUNTER))
                .setHeader("id", () -> "test")
                .to("direct:start");
    }


}
