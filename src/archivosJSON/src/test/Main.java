package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

/**
 * Basado en: http://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5
 * 
 * GSON se puede descargar en:
 * https://mvnrepository.com/artifact/com.google.code.gson/gson
 */
public class Main {

	public static void main(String[] args) {

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		Gson gson = new Gson();
		User juan = new User("Juan", 5);
		User maria = new User("Maria", 3);
		User[] lista = { juan, maria };

		System.out.println("Escritura a JSON");
		String ss = gson.toJson(lista);
		System.out.println(ss);

		System.out.println();
		System.out.println("Lectura de JSON (String) a Objeto");
		User[] lista2 = gson.fromJson(new StringReader(ss), User[].class);
		System.out.println(Arrays.toString(lista2));

		System.out.println();
		System.out.println("Lectura de JSON (file) a Objeto");

		String path = "./data/data.json";
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(path));
			Message[] lista3 = gson.fromJson(reader, Message[].class);
			System.out.println(Arrays.toString(lista3));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Lectura de JSON (file) a Objeto. Se lee solo un pedazo del JSON (body.data)");
		// Mirar 
		// https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.6
		// https://static.javadoc.io/com.google.code.gson/gson/2.8.6/com.google.gson/com/google/gson/JsonElement.html
		String path2 = "./data/data_anidated.json";
		JsonReader reader2;
		try {
			reader2 = new JsonReader(new FileReader(path2));
			JsonElement elem = JsonParser.parseReader(reader2);
			JsonElement e2 = elem.getAsJsonObject().get("body").getAsJsonObject().get("data");
			Message[] lista4 = gson.fromJson(e2, Message[].class);
			System.out.println(Arrays.toString(lista4));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("Lectura de JSON (file) a Objeto. Metodo manual");

		JsonReader reader3;
		try {
			List<Message> lista4 = readJsonStream(new FileInputStream(path));
			System.out.println(Arrays.toString(lista4.toArray()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<Message> readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readMessagesArray(reader);
		} finally {
			reader.close();
		}
	}

	public static List<Message> readMessagesArray(JsonReader reader) throws IOException {
		List<Message> messages = new ArrayList<Message>();

		reader.beginArray();
		while (reader.hasNext()) {
			messages.add(readMessage(reader));
		}
		reader.endArray();
		return messages;
	}

	public static Message readMessage(JsonReader reader) throws IOException {
		long id = -1;
		String text = null;
		User user = null;
		List<Double> geo = null;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("id")) {
				id = reader.nextLong();
			} else if (name.equals("text")) {
				text = reader.nextString();
			} else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
				geo = readDoublesArray(reader);
			} else if (name.equals("user")) {
				user = readUser(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Message(id, text, user, geo);
	}

	public static List<Double> readDoublesArray(JsonReader reader) throws IOException {
		List<Double> doubles = new ArrayList<Double>();

		reader.beginArray();
		while (reader.hasNext()) {
			doubles.add(reader.nextDouble());
		}
		reader.endArray();
		return doubles;
	}

	public static User readUser(JsonReader reader) throws IOException {
		String username = null;
		int followersCount = -1;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("name")) {
				username = reader.nextString();
			} else if (name.equals("followers_count")) {
				followersCount = reader.nextInt();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new User(username, followersCount);
	}

}
