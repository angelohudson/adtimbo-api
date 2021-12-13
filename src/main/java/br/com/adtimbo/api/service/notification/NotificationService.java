package br.com.adtimbo.api.service.notification;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import br.com.adtimbo.api.model.task.Task;

@Service
public class NotificationService {

	@Value("${app.firebase-configuration-file}")
	private String firebaseConfigPath;

	public void sendTasks(String title, String body, List<Task> tasks) {
		List<String> tokens = tasks.stream().filter(t -> t.getMembro().getDeviceToken() != null)
				.map(t -> t.getMembro().getDeviceToken()).collect(Collectors.toList());
		if (!tokens.isEmpty())
			this.sendAll(title, body, tokens);
	}

	public void sendSchedulers(List<Task> tasks) {
		tasks.forEach(t -> {
			if (t.getMembro().getDeviceToken() != null) {
				String title = t.getEvento().getTitulo();
				String subtitulo = t.getSubtitulo();
				this.send(title, subtitulo, t.getMembro().getDeviceToken());
			}
		});
	}

	public String send(String title, String body, String token) {
		Map<String, String> map = new HashMap<>();
		map.put("topico", "default");
		return this.send(title, body, token, map);
	}

	public String send(String title, String body, String token, Map<String, String> map) {
		try {
			Notification notification = Notification.builder().setBody(body).setTitle(title).build();
			Message message = Message.builder().setNotification(notification).setToken(token).putAllData(map).build();
			String response = FirebaseMessaging.getInstance().send(message);
			return response;
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BatchResponse sendAll(String title, String body, List<String> tokens) {
		Map<String, String> map = new HashMap<>();
		map.put("topico", "default");
		return this.sendAll(title, body, tokens, map);
	}

	public BatchResponse sendAll(String title, String body, List<String> tokens, Map<String, String> map) {
		try {
			Notification notification = Notification.builder().setBody(body).setTitle(title).build();
			MulticastMessage.Builder builder = MulticastMessage.builder();
			builder.setNotification(notification).addAllTokens(tokens).putAllData(map);
			MulticastMessage message = builder.build();
			BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
			return response;
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAccessToken() throws IOException {
		GoogleCredentials googleCredential = GoogleCredentials
				.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());
		googleCredential.refreshIfExpired();
		AccessToken accessToken = googleCredential.getAccessToken();
		return accessToken.getTokenValue();
	}

}
