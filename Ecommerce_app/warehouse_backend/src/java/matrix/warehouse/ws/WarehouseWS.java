/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.warehouse.ws;

import java.io.StringReader;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Navi-PC
 */
@RequestScoped
@ServerEndpoint("/orders")
public class WarehouseWS {
    private Session session;

	@OnMessage
	public void message(String message) {
		System.out.println(">>>>message = " + message);
	}

	@OnOpen
	public void open(Session sess) {
		session = sess;
		System.out.println(">>>connection opened: " + session.getId());
	}

	@OnClose
	public void close(CloseReason reason) {
		System.out.println(">>> connection closing: " + session.getId());
		System.out.println("\treason = " + reason.toString());
	}
        
        public void observeEvent(@Observes String message){
            System.out.println("inside observer");
            System.out.println(message);
            try {
                JsonReader jsonReader = Json.createReader(new StringReader(message));
                JsonObject object = jsonReader.readObject();
                jsonReader.close();
			for (Session s: session.getOpenSessions())
				s.getBasicRemote().sendObject(object);
		} catch (Throwable t) {
			t.printStackTrace();
			try { session.close(); } catch (Throwable tt) { }
		}
        }        
        
}
