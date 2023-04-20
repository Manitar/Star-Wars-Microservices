package bgu.spl.mics;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl mBus = null;
	//3 hash maps to work with.
	//First one: Map that assigns a microservice to a message type. Used to send messages to
	//Second one: Map that helps us understand for each event, which microservices are waiting, round robin.
	//Third one: Future map.
	private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>> EventsOfMicroservice;
	private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> SubscribersOfEvents;
	private final ConcurrentHashMap<Message, Future> futureMap;


	private MessageBusImpl(){
		//Initialize here
		EventsOfMicroservice = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>>();
		SubscribersOfEvents = new ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>();
		futureMap = new ConcurrentHashMap<Message, Future >();
	}

	public static MessageBusImpl getInstance() {
		if (mBus == null) {
			mBus = new MessageBusImpl();
		}
		return mBus;
	}


	@Override //Does this have to be synchronized? Because notifyAll?
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		String eventType = type.getName(); //class name to string
		SubscribersOfEvents.putIfAbsent(eventType, new ConcurrentLinkedQueue<String>());
		EventsOfMicroservice.putIfAbsent(m.getName(), new ConcurrentLinkedQueue<Message>());

		//If the eventQueue doesn't have the microservice subscribed to this event
		SubscribersOfEvents.get(eventType).add(m.getName()); //Subscribe him to the event
		//}

	}

	@Override //Does this have to be synchronized? Because notifyAll?
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		String eventType = type.getName(); //Class name to string
		SubscribersOfEvents.putIfAbsent(eventType, new ConcurrentLinkedQueue<String>());
		SubscribersOfEvents.get(eventType).add(m.getName()); //subscribe him to the broadcast

	}
	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		futureMap.get(e).resolve(result);


		futureMap.remove(e);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		String eventType = b.getClass().getName(); //Class name to string
		if(SubscribersOfEvents.containsKey(eventType)){ //If there is a micro in the queue that is registered to the broadcast type
			for (String micro : SubscribersOfEvents.get(eventType)) //For every micro in the queue that is registered to the b type
			{
				EventsOfMicroservice.get(micro).add(b); //Add the broadcast to it's queue in mainQueue
			}
		}
		//if the broadcast was BOOMBED - than clear SubOfEvents - we know there will be no more Messages!
	}

	@Override
	public  <T> Future <T> sendEvent(Event<T> e) {

		String eventType = e.getClass().getName(); //Gets the name of event type (Attack, Bomb destroyer etc)
		if(SubscribersOfEvents.containsKey(eventType) && !(SubscribersOfEvents.get(eventType).isEmpty())){ //Asks if someone is subscribed to the current event type
			String topMicro = SubscribersOfEvents.get(eventType).poll(); //Pulls microservice from event queue
			EventsOfMicroservice.get(topMicro).add(e); //Put the event into the main queue, so the microservice gets it
			SubscribersOfEvents.get(eventType).add(topMicro); //Puts the microservice back into the event queue, round robin implementation
			Future result = new Future(); //Create new Future
			futureMap.put(e, result); //Put the future in the hash map
			return result;
		}
		return null;
	}



	@Override
	public void register(MicroService m) {
		ConcurrentLinkedQueue<Message> q = new ConcurrentLinkedQueue<Message>();
 		EventsOfMicroservice.putIfAbsent(m.getName(), q);

	}

	@Override
	public void unregister(MicroService m) {
		EventsOfMicroservice.remove(m.getName());

		//The reason we type the line below is to be able to test jsons one after another, to clear the
		//map for upcoming tests.
		SubscribersOfEvents.clear();



	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		while(EventsOfMicroservice.get(m.getName()).isEmpty()){
			//Keep waiting here until it isn't empty
		}
		return EventsOfMicroservice.get(m.getName()).poll();
	}
}