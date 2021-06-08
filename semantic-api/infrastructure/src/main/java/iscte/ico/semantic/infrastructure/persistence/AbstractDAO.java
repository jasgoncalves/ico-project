package iscte.ico.semantic.infrastructure.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
public abstract class AbstractDAO< T extends Serializable>{
    private Class< T > clazz;

    @Autowired
    private SessionFactory sessionFactory;

    public void setClazz(Class< T > clazzToSet) {
        clazz = clazzToSet;
    }

    public T findOne(UUID id) {
        return (T) getCurrentSession().get( clazz, id );
    }
    public List< T > findAll() {
        return getCurrentSession()
                .createQuery( "from " + clazz.getName() ).list();
    }

    public void save(T entity) {

        getCurrentSession().persist( entity );

    }

    public T update(T entity) {
        return (T) getCurrentSession().merge( entity );
    }

    public void delete(T entity) {
        getCurrentSession().delete( entity );
    }

    public void deleteById(UUID id) {
        final T entity = findOne( id);
        delete( entity );
    }

    protected final Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
