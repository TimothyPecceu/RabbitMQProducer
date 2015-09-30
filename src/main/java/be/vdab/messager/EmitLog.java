package be.vdab.messager;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {
	
	private static final String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type a message or 'quit' to close: ");
		String message = scanner.nextLine();
		
		while(!message.equals("quit")){
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			System.out.println("Type a message or 'quit' to close: ");
			message = scanner.nextLine();
		}
		
		scanner.close();
		channel.close();
		connection.close();
	}
}
