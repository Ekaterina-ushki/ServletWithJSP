package services;

import dbService.data.UserData;

import java.util.HashMap;
import java.util.Map;

public class SessionService {
    private static SessionService Instance = new SessionService();
    private final Map<String, UserData> sessionIdToProfile;

    private SessionService() {
        sessionIdToProfile = new HashMap<String, UserData>();
    }

    public static SessionService getInstance() {
        return Instance;
    }

    public UserData getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserData userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
