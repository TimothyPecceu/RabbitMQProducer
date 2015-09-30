package be.vdab.messager;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
	
	private static final String EXCHANGE_NAME = "direct_logs";
	
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type a severity level ('info', 'warning', 'error') or 'quit' to close: ");
		String severity = scanner.nextLine();
		
		while(!severity.equals("quit")){
			System.out.println("Type an error message: ");
			String message = scanner.nextLine();
			channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
			System.out.println(" [x] Sent '" + severity+ ":"+ message + "'");
			System.out.println("Type a severity level ('info', 'warning', 'error') or 'quit' to close: ");
			severity = scanner.nextLine();
		}
		
		scanner.close();
		channel.close();
		connection.close();
	}
}
