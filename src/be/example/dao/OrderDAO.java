package be.example.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import be.example.entities.Order;
import be.example.exceptions.DAOException;

@Stateless
public class OrderDAO<Obj> {

	@PersistenceContext(unitName="bdd_tp_PU")
	private EntityManager em;
	
	private final static String JPQL_ALL_ORD = "SELECT o FROM Order o";
	
	public void create(Order order) throws DAOException{
		try {
			em.persist(order);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		
	}
	
	public Order readByID(Long id) throws DAOException{
		try {
			return em.find(Order.class,id);
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
	
	public List<Order> readAll() throws DAOException{
		try {
			TypedQuery query = em.createQuery(JPQL_ALL_ORD,Order.class);
			return query.getResultList();
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
	
	public void delete(Order order) throws DAOException{
		try {
			em.remove(em.merge(order));
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
}
