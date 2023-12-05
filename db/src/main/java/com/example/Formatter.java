package com.example;

import com.example.banco.struct.tables.schemas.ClientRegistry;
import com.example.banco.struct.tables.schemas.VehicleSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Formatter {
    public static String Request(String tableName, String order, Object body) throws JsonMappingException, JsonProcessingException {
        // objetivo request = {body: {...}, metadata: {tablename:--, order:--}}
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJson = objectMapper.createObjectNode();
        
        ObjectNode metaNode = objectMapper.createObjectNode();
        metaNode.put("tableName", tableName);
        metaNode.put("order", order);

        requestJson.set("metadata", metaNode);
        
        switch (tableName) {
            case "User":
                switch (order) {
                    case "Create":
                    case "Update":
                        try {
                            String jsonUser = objectMapper.writeValueAsString((ClientRegistry) body);
                            requestJson.set("body", objectMapper.readTree(jsonUser));
                        } catch (JsonProcessingException e) {
                            System.err.println("Recebido parametros (" + tableName + "," + order + "," + body + ")");
                            System.err.println("Operacao invalida ao parsear isso para string");
                        } catch (ClassCastException e) {
                            System.err.println("Recebido parametros (" + tableName + "," + order + ")");
                            System.err.println("Tentou parsear " + body + " para ClientRegisrt");
                            System.err.println("Operacao invalida ao parsear isso para string");
                        }
                        break;
                    
                    case "Read":
                    case "Delete":
                    case "ReadMore":
                        requestJson.put("body", (String) body);
                        break;
                    
                    default:
                        break;       
                }
                break;

            case "Vehicle":
                switch (order) {
                    case "Create":
                    case "Update":
                        try {
                            String jsonVehicle = objectMapper.writeValueAsString((VehicleSchema) body);
                            requestJson.set("body", objectMapper.readTree(jsonVehicle));
                        } catch (JsonProcessingException e) {
                            System.err.println("Recebido parametros (" + tableName + "," + order + "," + body + ")");
                            System.err.println("Operacao invalida ao parsear isso para string");
                        }
                        break;

                    case "Read":
                    case "Delete":
                    case "ReadAvailableAndGroup":
                        requestJson.put("body", (String) body);
                        break;
                    
                    case "ReadAvailable":
                    case "GetAll":
                        requestJson.put("body", "");
                        break;
                
                    default:
                        break;
                }
                break;
            case "Registry":
                switch (order) {
                    case "Read":
                        requestJson.put("body", (String) body);
                        break;
                    
                    default:
                        break;
                }
                break;
            
            default:
                break;
        }
        String request = objectMapper.writeValueAsString(requestJson);
        return request;
    }
}
