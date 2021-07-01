package reproducer;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class MyStringAggregationStrategy implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange == null) {
                return newExchange;
            }
            String body1 = oldExchange.getIn().getBody(String.class);
            String body2 = newExchange.getIn().getBody(String.class);

            oldExchange.getIn().setBody(body1 + "," + body2);
            return oldExchange;
        }
    }