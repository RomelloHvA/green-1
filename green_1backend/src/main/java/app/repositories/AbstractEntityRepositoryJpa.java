package app.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public abstract class AbstractEntityRepositoryJpa<E extends Identifiable>
        implements EntityRepository<E> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> theEntityClass;

    public AbstractEntityRepositoryJpa(Class<E> entityClass) {
        this.theEntityClass = entityClass;
        System.out.println("Created " + this.getClass().getName() +
                "<" + this.theEntityClass.getSimpleName() + ">");
    }

    @Override
    public List<E> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(theEntityClass);
        Root<E> rootEntry = cq.from(theEntityClass);
        CriteriaQuery<E> all = cq.select(rootEntry);

        TypedQuery<E> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public E findById(long id) {
        return entityManager.find(this.theEntityClass, id);    }

    @Override
    @Transactional
    public E save(E entity) {
        if (entity.getId() == 0) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    @Transactional
    public E deleteById(long id) {
        E entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
        return entity;
    }

    @Override
    public List<E> findByQuery(String jpqlName, Object... params) {
        try {
            TypedQuery<E> query = entityManager.createNamedQuery(jpqlName, theEntityClass);

            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i + 1, params[i]);
                }
            }
            return query.getResultList();
        } catch (IllegalArgumentException ex) {
            // Handle the case where the named query does not exist
            throw new IllegalArgumentException("Invalid named query: " + jpqlName, ex);
        }
    }
}
