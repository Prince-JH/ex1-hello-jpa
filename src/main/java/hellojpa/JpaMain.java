package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFood().add("치킨");
            member.getFavoriteFood().add("피자");
            member.getFavoriteFood().add("족발");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "1"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "2"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===============");
            Member findMember = em.find(Member.class, member.getId());

            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFood().remove("치킨");
            findMember.getFavoriteFood().add("한식");


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
