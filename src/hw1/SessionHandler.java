package hw1;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

public class SessionHandler {
	private static final Set<Session> sessions = new HashSet<>();
	
	public static void addSession(Session session) {
		sessions.add(session);
	}
	
	public static void removeSession(Session session) {
		sessions.remove(session);
	}
	
	public static void sendToAllConnectedSessions(String message) {
		for (Session session : sessions) {
			sendToSession(session, message);
		}
	}
	
	public static void sendToSession(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException ex) {
			sessions.remove(session);
		}
	}
}
