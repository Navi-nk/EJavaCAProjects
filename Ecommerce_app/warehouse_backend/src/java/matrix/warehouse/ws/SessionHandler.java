package matrix.warehouse.ws;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 *
 * @author Navi
 */
@ApplicationScoped
public class SessionHandler {

    private final Set<Session> sessions = new HashSet<>();

    public Set<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void closeAllSessions() {
        sessions.forEach(s -> {
            try {
                s.close();
            } catch (Throwable t) {
                t.getStackTrace();
            }
        });
        sessions.clear();
    }

}
