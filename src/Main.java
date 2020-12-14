import java.util.UUID;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSecurityException;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

public class Main {

	static String subscriberID = UUID.randomUUID().toString();
	static MqttAsyncClient subscriber;
	static MqttConnectionOptions options;

	public class SimpleMQTTCallBack implements MqttCallback {

		public void authPacketArrived(int arg0, MqttProperties arg1) {
			// TODO Auto-generated method stub

		}

		public void connectComplete(boolean arg0, String arg1) {
			System.out.println("Subscriber successfully connected: " + arg1);

		}

		public void deliveryComplete(IMqttToken arg0) {
			// TODO Auto-generated method stub

		}

		public void disconnected(MqttDisconnectResponse arg0) {
			// TODO Auto-generated method stub

		}

		public void messageArrived(String arg0, MqttMessage arg1)
				throws Exception {
			System.out.println("Subscriber heard message at container broker: " + arg1.toString());
		}

		public void mqttErrorOccurred(MqttException arg0) {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String[] args) throws MqttException {
		// create a subscriber
		subscriber = new MqttAsyncClient("tcp://localhost:9997", subscriberID);

		// set the callback for the subscriber
		SimpleMQTTCallBack callback = new Main().new SimpleMQTTCallBack();
		subscriber.setCallback(callback);

		// set connection options for subscriber
		setupConnections(subscriber);

		// once connected then subscribe to Testing topic
		MqttSubscription mqttsub = new MqttSubscription("Testing");
		subscriber.subscribe(mqttsub);
	}

	public static void setupConnections(MqttAsyncClient mqttClient) {
		System.out.println("Options setting up");
		options = new MqttConnectionOptions();
		options.setAutomaticReconnect(false);
		options.setCleanStart(true);
		options.setAutomaticReconnect(true);

		try {
			mqttClient.connect(options).waitForCompletion();
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
