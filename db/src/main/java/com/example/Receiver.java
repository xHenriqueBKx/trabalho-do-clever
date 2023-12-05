package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.banco.struct.tables.Registry;
import com.example.banco.struct.tables.User;
import com.example.banco.struct.tables.Vehicle;
import com.example.banco.struct.tables.schemas.ClientRegistry;
import com.example.banco.struct.tables.schemas.Group;
import com.example.banco.struct.tables.schemas.RentingRegistry;
import com.example.banco.struct.tables.schemas.VehicleSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.rabbitmq.client.*;

public class Receiver {

    private final static String QUEUE_NAME = "canal_data_base";
    private final static String RESPONSE_QUEUE_NAME = "canal_backend";

    public static Object analize(String message) throws JsonMappingException, JsonProcessingException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);

        JsonNode metadata = jsonNode.get("metadata");
        
        //  Modelo de mensagem recebida: 
        //      json: {"body": Object, "metadata": {"tableName": ---, "order": ---}}

        String      tableName   = metadata.get("tableName").asText();
        String      order       = metadata.get("order").asText();
        JsonNode    body        = jsonNode.get("body");

        switch (tableName) {
            case "User": // Ordem para Tabela User
                User user = new User(); 
                ClientRegistry client;
                String cpf;

                switch (order) {
                    case "Create":
                        client = objectMapper.treeToValue(body, ClientRegistry.class);
                        return user.Create(client);

                    case "Read":
                        cpf = body.get("cpf").asText();
                        System.out.println(cpf);
                        return user.Read(cpf);

                    case "Update":
                        client = objectMapper.treeToValue(body, ClientRegistry.class);
                        return user.Update(client);

                    case "Delete":
                        cpf = body.get("cpf").asText();
                        return user.Read(cpf);

                    case "ReadMore":
                        String condition = body.get("condition").asText();
                        return user.ReadMore(condition);    

                    default:
                        return false;

                }

            case "Vehicle": // Ordem para Tabela Vehicle
                Vehicle vehicle = new Vehicle();
                VehicleSchema vehicleSchema;
                String plate;
                switch (order) {
                    case "Create":
                        vehicleSchema = objectMapper.treeToValue(body, VehicleSchema.class);
                        return vehicle.Create(vehicleSchema);

                    case "Read":
                        plate = body.get("licensePlate").asText();
                        return vehicle.Read(plate);

                    case "Update":
                        vehicleSchema = objectMapper.treeToValue(body, VehicleSchema.class);
                        return vehicle.Update(vehicleSchema);

                    case "Delete":
                        plate = body.get("licensePlate").asText();
                        return vehicle.Delete(plate);

                    case "ReadAvailable":
                        return vehicle.ReadAvailable();

                    case "ReadAvailableAndGroup":
                        Group group = Group.valueOf( body.get("group").asText() );
                        return vehicle.ReadAvailableAndGroup(group);

                    case "GetAll":
                        return vehicle.GetAll();    

                    default:
                        return false;
                    
                }
            
            case "Registry": // Ordem para Tabela Registry
                Registry registry = new Registry();
                RentingRegistry rentingRegistry;
                String id;
                switch (order) {
                    case "Read":
                        id = body.get("id").asText();
                        return registry.Read(id);
                    
                    case "NewRegistry":
                        return registry.NewRegistry();
                    
                    default:
                        return false;
                }                                
            default: // Ordem para Tabela Não Reconhecida ou Inexistente 
                return false;
        }
    }

    public static void main(String[] argv) throws Exception , SQLException {        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        ObjectMapper objectMapper = new ObjectMapper();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String jsonMessage = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received message");
            
            try {
                Object response = analize(jsonMessage);
                String message = "";
                ObjectNode json = objectMapper.createObjectNode();

                switch (response.getClass().getName().toString()) {
                    case "java.lang.Boolean":
                        // Resposta é um Booleano
                        
                        json.put("Boolean", (boolean) response);                         
                        break;

                    case "java.lang.Integer":
                        // Resposta é um Inteiro                        
                        
                        json.put("Inteiro", (int) response);
                        break;

                    case "java.lang.String":
                        // Resposta é uma String
                        
                        json.put("String", (String) response);
                        break;

                    case "com.example.banco.struct.tables.schemas.VehicleSchema":
                        // Resposta é um Veículo
                        
                        String jsonVehicle = objectMapper.writeValueAsString(response);
                        json.set("Vehicle", objectMapper.readTree(jsonVehicle));

                        break;
                        
                    case "com.example.banco.struct.tables.schemas.ClientRegistry":
                        // Resposta é um Cliente

                        String jsonClient = objectMapper.writeValueAsString(response);
                        json.set("Client", objectMapper.readTree(jsonClient));

                        break;

                    case "java.util.ArrayList":
                        System.out.println("É um array");
                        if (( (ArrayList) response).isEmpty()) {
                            // Resposta é um Array Vazio
                            sendResponse(channel, delivery.getProperties().getReplyTo(), "Array Vazio");        
                            return;                            
                        }

                        switch (((ArrayList)response).get(0).getClass().getName().toString()) {
                            case "com.example.banco.struct.tables.schemas.VehicleSchema":
                                // Resposta é um Array de Veículo
                                
                                message = Serialize.serializeArray( (ArrayList) response, "ArrayVehicle");
                                break;

                            case "com.example.banco.struct.tables.schemas.ClientRegistry":
                                // Resposta é um Array de Cliente
                                
                                message = Serialize.serializeArray( (ArrayList) response, "ArrayClient");
                                break;

                            default:
                                System.out.println("indefinido");
                                sendResponse(channel, delivery.getProperties().getReplyTo(), "Array Undefined");        
                                return;
                        }

                        sendResponse(channel, delivery.getProperties().getReplyTo(), message);
                        return;
                    
                    default:
                        System.out.println("indefinido");
                }

                message = objectMapper.writeValueAsString(json);

                sendResponse(channel, delivery.getProperties().getReplyTo(), message);
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
      }
    
    private static void sendResponse(Channel channel, String replyToQueue, String message) throws Exception {
        // Configurar a fila de resposta
        channel.queueDeclare(RESPONSE_QUEUE_NAME, false, false, false, null);

        // Mensagem de resposta (por exemplo, 0)

        // Publicar a mensagem de resposta na fila de resposta
        channel.basicPublish("", replyToQueue, null, message.getBytes());
        System.out.println(" [x] Resposta enviada: '" + message + "' para a fila '" + replyToQueue + "'");
    }
}