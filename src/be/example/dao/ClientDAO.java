package be.example.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import be.example.entities.Client;
import be.example.exceptions.DAOException;

@Stateless
public class ClientDAO {
	
	@PersistenceContext(unitName="bdd_tp_PU")
	private EntityManager em;
	
	private final static String JPQL_ALL_CLI = "SELECT c FROM Client c";
	
	public void create(Client client) throws DAOException{
		try {
			em.persist(client);
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
	
	public Client readByID(Long id) throws DAOException{		
		try {
			return em.find(Client.class,id);
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		
	}
	
	public List<Client> readAll() throws DAOException{
		try {
			TypedQuery query = em.createQuery(JPQL_ALL_CLI,Client.class);
			return query.getResultList();
		}catch(Exception e) {
			throw new DAOException(e);
		}
		
	}
	
	public void delete(Client client) throws DAOException{
		try {
			em.remove(em.merge(client));
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
}
