/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.warehouse.business;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import matrix.warehouse.model.CartItem;
import matrix.warehouse.model.Order;

/**
 *
 * @author Navi-PC
 */
@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager em;

    public List<Order> getAllOrder() {
        TypedQuery<Order> query = em.createQuery("select o from Order o", Order.class);
        return (query.getResultList());
    }

    public void storeOrder(Order order) {
        em.persist(order);
    }

    //This is because we dont want to use the transaction from the mdb. If an exception occurs at this point
    //we want to rollback the db transaction and not the message transaction.
    //It is observed that if a transaction fails at mdb due to exception then it goes into a infinite loop.
    //This meeans the queue is continously trying to push the message to mdb.
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistOrder(String message) {
        Order o = new Order();
        List<CartItem> cartItems = new LinkedList<>();

        JsonObject object;
        try (JsonReader jsonReader = Json.createReader(new StringReader(message))) {
            object = jsonReader.readObject();

            o.setCustomerName(object.getString("name"));
            o.setComment(object.getString("comment"));
            o.setAddress(object.getString("address"));

            JsonArray cart = object.getJsonArray("cart");
            cart.forEach((item) -> {
                CartItem c = new CartItem();
                c.setItemName(((JsonObject) item).getString("item"));
                c.setQuantity(((JsonObject) item).getInt("quantity"));
                cartItems.add(c);
            });

            o.setCartItems(cartItems);

            storeOrder(o);

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}
