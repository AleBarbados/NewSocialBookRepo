package socialbook.utility;


import socialbook.model.GestioneDatabase.Cart;
import socialbook.model.GestioneDatabase.CartDAO;
import socialbook.model.GestioneDatabase.Order;
import socialbook.model.GestioneDatabase.OrderDAO;

import javax.servlet.http.HttpServletRequest;

public class CartManagement {

    public static void doManageCart(int id_customer, String addr){

        Order o = new OrderDAO().doRetrieveByCart(id_customer);
        o.setInvoice_addr(addr);
        new OrderDAO().doUpdate(o);

        Cart cart = new Cart(id_customer, 0);
        CartDAO cartDAO = new CartDAO();
        cartDAO.doSave(cart, id_customer);
    }
}
