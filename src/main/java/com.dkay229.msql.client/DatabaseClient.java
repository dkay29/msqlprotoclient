package com.dkay229.msql.client;

import com.dkay229.msql.proto.DatabaseServiceGrpc;
import com.dkay229.msql.proto.Dbserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DatabaseClient {
    public static void main(String[] args) {
        // Step 1: Create a channel to the server at localhost:8080
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext() // Disable SSL/TLS for simplicity (use plaintext)
                .build();

        // Step 2: Create a blocking stub (a synchronous client)
        DatabaseServiceGrpc.DatabaseServiceBlockingStub stub = DatabaseServiceGrpc.newBlockingStub(channel);

        // Step 3: Build a request
        Dbserver.QueryRequest request = Dbserver.QueryRequest.newBuilder()
                .setSql("SELECT * FROM users")
                .build();

        // Step 4: Send the request to the server and receive the response
        Dbserver.QueryResponse response = stub.executeQuery(request);

        // Step 5: Print the response
        System.out.println("Received from server: " + response.getRows(0));

        // Step 6: Shutdown the channel

        Dbserver.SampleRequest sampleRequest = Dbserver.SampleRequest.newBuilder()
                .build();
        Dbserver.SampleMessage sampleMessage = stub.getRandomSampleMessage(sampleRequest);
        System.out.println("Received from server: " + sampleMessage);
        channel.shutdown();
    }
}
