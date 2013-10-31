package com;

import com.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class HistoryInsertionTest {
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.setNamingStrategy(ImprovedNamingStrategy.INSTANCE).buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Person fredFlinstone = new Person("Fred", "Flinstone", dateFormat.parse("04/30/1980"));
            Person wilmaFlinstone = new Person("Wilma", "Flinstone", dateFormat.parse("1/28/1985"));
            Person barnyRubble = new Person("Barny", "Rubble", dateFormat.parse("6/18/1982"));
            Person bettyRubble = new Person("Betty", "Rubble", dateFormat.parse("2/23/1987"));

            saveOrUpdateObject(fredFlinstone);
            saveOrUpdateObject(wilmaFlinstone);
            saveOrUpdateObject(barnyRubble);
            saveOrUpdateObject(bettyRubble);

            wilmaFlinstone.setLastName("Bedrock");
            bettyRubble.setLastName("Bedrock");
            saveOrUpdateObject(wilmaFlinstone);
            saveOrUpdateObject(bettyRubble);

            deleteObject(fredFlinstone);
            deleteObject(wilmaFlinstone);
            deleteObject(barnyRubble);
            deleteObject(bettyRubble);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void getManagedEntities() {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }

    public static void saveOrUpdateObject(Object object) {
        final Session session = getSession();
        try {
            session.beginTransaction(); //pretend it's a shared session
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public static void deleteObject(Object object) {
        final Session session = getSession();
        try {
            session.beginTransaction(); //pretend it's a shared session
            session.delete(object);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}