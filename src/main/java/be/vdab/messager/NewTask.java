package be.vdab.messager;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private final static String TASK_QUEUE_NAME = "task_queue2";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type a message or 'quit' to close: ");
		String message = scanner.nextLine();
		while(!message.equals("quit")){
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
			System.out.println("Type a message or 'quit' to close: ");
			message = scanner.nextLine();
		}
		
		
		channel.close();
		connection.close();
		scanner.close();
	}

	/*private static String getMessage(String[] strings) {
		if (strings.length < 1) {
			return "Hello World!";
		}
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0) {
			return "";
		}
		StringBuilder words = new StringBuilder(strings[0]);
		for (String string : strings) {
			words.append(delimiter).append(string);
		}
		return words.toString();
	}*/
}
