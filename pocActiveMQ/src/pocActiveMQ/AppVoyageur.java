package pocActiveMQ;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.MessageProducer;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Destination;
import jakarta.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppVoyageur {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination receiveQueue = session.createQueue("voyageurQueue");
        Destination sendQueue = session.createQueue("amiQueue");

        MessageConsumer consumer = session.createConsumer(receiveQueue);
        MessageProducer producer = session.createProducer(sendQueue);

        Message message = consumer.receive();
        if (message instanceof TextMessage) {
            String text = ((TextMessage) message).getText();
            System.out.println("AppVoyageur a reçu : " + text);

            if (text.equalsIgnoreCase("Qui est là")) {
                TextMessage reply = session.createTextMessage("Present");
                producer.send(reply);
                System.out.println("AppVoyageur a répondu : Present");
            }
        }

        connection.close();
    }
}
