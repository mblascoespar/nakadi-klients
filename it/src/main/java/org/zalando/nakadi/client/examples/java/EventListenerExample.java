package org.zalando.nakadi.client.examples.java;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.zalando.nakadi.client.java.Client;
import org.zalando.nakadi.client.java.Listener;
import org.zalando.nakadi.client.java.StreamParameters;
import org.zalando.nakadi.client.java.model.Cursor;
import org.zalando.nakadi.client.java.model.EventStreamBatch;
import org.zalando.nakadi.client.scala.ClientFactory;

import com.fasterxml.jackson.core.type.TypeReference;

public class EventListenerExample {

	/**
	 * Implement the Listener interface
	 */

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		/**
		 * Create client
		 */
		final Client client = ClientFactory.getJavaClient();

		/**
		 * Initialize our Listener
		 */
		Listener<MeetingsEvent> listener = new EventCounterListener("Java-Test");

		StreamParameters params = new StreamParameters(
				Optional.of(new Cursor("0", "BEGIN")),
				Optional.empty(),//batchLimit,
				Optional.empty(),//streamLimit,
				Optional.empty(),//batchFlushTimeout,
				Optional.empty(),//streamTimeout,
				Optional.empty(),//streamKeepAliveLimit,
				Optional.empty()//flowId
				);

		String eventTypeName = "Event-example-with-0-messages";
		TypeReference<EventStreamBatch<MeetingsEvent>> typeRef = new TypeReference<EventStreamBatch<MeetingsEvent>>() {};

		java.util.concurrent.Future<Void> result = client.subscribe(eventTypeName, params, listener, typeRef);

		result.get();
	}
}
