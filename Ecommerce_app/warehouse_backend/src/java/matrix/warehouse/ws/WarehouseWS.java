package matrix.warehouse.ws;

import java.io.StringReader;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.DefaultValue;
import matrix.warehouse.business.OrderBean;
import matrix.warehouse.model.Order;

/**
 *
 * @author Navi
 */
@RequestScoped
@ServerEndpoint("/orders/{history}")
public class WarehouseWS {

    @Inject
    private SessionHandler sessionHandler;
    @EJB
    private OrderBean orderBean;

    @OnMessage
    public void message(String message) {
        System.out.println("message received = " + message);

    }

    @OnOpen
    public void open(Session session,
            @DefaultValue("false") @PathParam("history") Boolean isHistory) {
        sessionHandler.addSession(session);
        System.out.println("connection opened: " + session.getId() + " " + isHistory);
        if (isHistory) {
            List<Order> orders = orderBean.getAllOrder();

            try {
                for (Order o : orders) {

                    for (Session s : session.getOpenSessions()) {
                        System.out.println("matrix.warehouse.ws.WarehouseWS.open()");
                        s.getBasicRemote().sendObject(o.toJson());
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
                try {
                    session.close();
                } catch (Throwable tt) {
                }
            }
        }
    }

    @OnClose
    public void close(Session session) {
        System.out.println("connection closed: " + session.getId());
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
        sessionHandler.closeAllSessions();
    }

    public void observeEvent(@Observes String message) {
        System.out.println("inside observer");
        System.out.println(message);
        try {
            JsonObject object;
            try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
                object = jsonReader.readObject();
            }

            for (Session s : sessionHandler.getSessions()) {
                s.getBasicRemote().sendObject(object);
            }

        } catch (Throwable t) {
            t.printStackTrace();
            sessionHandler.closeAllSessions();
        }
    }
}
