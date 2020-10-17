package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static AccountService Instance = new AccountService();
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    private AccountService() {
        loginToProfile = new HashMap<String, UserProfile>();
        sessionIdToProfile = new HashMap<String, UserProfile>();

        loginToProfile.put("Kate", new UserProfile("Kate", "123456", "malyavkina-00@mail.ru"));
        loginToProfile.put("11", new UserProfile("11", "11", "1111@11"));
    }

    public static AccountService getInstance() {
        return Instance;
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}

