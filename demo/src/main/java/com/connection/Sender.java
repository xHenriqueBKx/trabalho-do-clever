package com.connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Sender {

    private final static String QUEUE_NAME = "canal_data_base";
    private final static String RESPONSE_QUEUE_NAME = "canal_backend";

    public static void send(String table, String order, Object body) {
        // Configuração da conexão com o RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Endereço do servidor RabbitMQ
        Connection connection;
        try {
            connection = factory.newConnection();
        
            Channel channel = connection.createChannel();
            // Declarar as filas
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(RESPONSE_QUEUE_NAME, false, false, false, null);
   
            // preparando para receber callback
            waitForResponse(channel);
   
            // Objeto que será enviado
            String message = Formatter.Request(table, order, body);
            
            // Adicionando a propriedade de callback à fila
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .replyTo(RESPONSE_QUEUE_NAME)
                .build();
            
            // Publicando as mensagens na fila
            channel.basicPublish("", QUEUE_NAME, props, message.getBytes());
            System.out.println(" [x] Mensagem enviada: '" + message + "'");
        } catch (IOException | TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void waitForResponse(Channel channel) throws Exception {
        // Consumidor para a fila de resposta
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String responseMessage = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Resposta recebida: '" + responseMessage + "'");
        };

        channel.basicConsume(RESPONSE_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}