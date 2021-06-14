package jpashop.jpabook;

import jpashop.jpabook.domain.Delivery;
import jpashop.jpabook.domain.DeliveryStatus;
import jpashop.jpabook.domain.Order;
import jpashop.jpabook.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Delivery delivery = new Delivery();
            delivery.setCity("seoul");
            delivery.setStreet("가로수길");
            delivery.setZipcode("110202");
            delivery.setStatus(DeliveryStatus.DELIVERY);

            em.persist(delivery);

            Order order = new Order();
            order.setStatus(OrderStatus.ORDER);

            order.addDelivery(delivery);

            em.persist(order);

            Order findOrder = em.find(Order.class, order.getId());
            System.out.println(findOrder.getDelivery().getStatus());

            Delivery findDelivery = em.find(Delivery.class, delivery.getId());
            System.out.println(findDelivery.getOrder().getStatus());

            System.out.println("============");
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
