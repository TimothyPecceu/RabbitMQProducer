package be.vdab.messager;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {
	
	private static final String EXCHANGE_NAME = "topic_logs";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type a routing key (example: kern.critical) or 'quit' to close: ");
		String routingKey = scanner.nextLine();
		
		while(!routingKey.equals("quit")){
			System.out.println("Type a message: ");
			String message = scanner.nextLine();
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
			System.out.println(" [x] Sent '" + routingKey+ ":"+ message + "'");
			System.out.println("Type a routing key (example: kern.critical) or 'quit' to close: ");
			routingKey = scanner.nextLine();
		}
		
		scanner.close();
		channel.close();
		connection.close();
	}
}
