package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address homeAddress = new Address("city", "street", "zipcode");

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAddress(homeAddress);

            Address addressClone = new Address(homeAddress.getCity(), homeAddress.getStreet(), homeAddress.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(addressClone);

            em.persist(member1);
            em.persist(member2);

            member1.getHomeAddress().setCity("newCity");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
