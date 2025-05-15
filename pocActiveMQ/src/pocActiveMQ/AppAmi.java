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

public class AppAmi {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination sendQueue = session.createQueue("voyageurQueue");
        Destination responseQueue = session.createQueue("amiQueue");

        MessageProducer producer = session.createProducer(sendQueue);
        MessageConsumer consumer = session.createConsumer(responseQueue);

        TextMessage message = session.createTextMessage("Qui est là");
        producer.send(message);
        System.out.println("AppAmi a envoyé : " + message.getText());

        Message response = consumer.receive(50000);
        if (response instanceof TextMessage) {
            System.out.println("AppAmi a reçu : " + ((TextMessage) response).getText());
        } else {
            System.out.println("Pas de réponse reçue.");
        }

        connection.close();
    }
}
