package org.hibernate.tutorial.domain;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import junit.framework.TestCase;

/**
 * Illustrates use of Hibernate native APIs.
 *
 * @author Steve Ebersole
 */
	public class NativeApiIllustrationTest extends TestCase {
        private SessionFactory sessionFactory;

        @Override
        protected void setUp() throws Exception {
                // A SessionFactory is set up once for an application!
        	    StandardServiceRegistry registry = null;
        	    try {
        	         registry = new StandardServiceRegistryBuilder()
                                .configure() // configures settings from hibernate.cfg.xml
                                .build();
                } catch (Exception e) {
                    System.out.println( "Error during connection");
                }
                try {
                        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
                }
                catch (Exception e) {
                        // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                        // so destroy it manually.
                        StandardServiceRegistryBuilder.destroy( registry );
                }
        }

            @Override
            protected void tearDown() throws Exception {
                    if ( sessionFactory != null ) {
                            sessionFactory.close();
                    }
            }

            @SuppressWarnings("unchecked")
            public void testBasicUsage() {
                    // create a couple of events...
                    Session session = sessionFactory.openSession();
                    session.beginTransaction();
                    session.save( new Event( "Our very first event!", new Date() ) );
                    session.save( new Event( "A follow up event", new Date() ) );
                    session.getTransaction().commit();
                    session.close();

                    // now lets pull events from the database and list them
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    List result = session.createQuery( "from Event" ).list();
                    for ( Event event : (List<Event>) result ) {
                        System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
                    }
                    session.getTransaction().commit();
                    session.close();
            }
	
}
