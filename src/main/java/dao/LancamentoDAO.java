package dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import domain.Lancamento;

@Dependent
public class LancamentoDAO extends AModelDAO<Lancamento> implements Serializable {
	
	private static final long serialVersionUID = 2770797811414910930L;
	
	public LancamentoDAO() {
		super(Lancamento.class);
	}
	
	public Double consulta_item10(ItemDAO itemDAO) {
		String name = this.getEntityName();
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		
		Query q = em.createQuery(
				String.format(
						"select sum(l.valorTotal) from %s l where (select avg(i.valor) from %s i where i member of l.itens) > 100",
				name, itemDAO.getEntityName()),
				Double.class
		);
		Double r = (Double) q.getSingleResult();
		
		if (r == null) {
			return 0.0;
		}
		
		em.close();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> consulta_item11() {
		String name = this.getEntityName();
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		
		Query q = em.createQuery(
				String.format(
						"select l from %s l where l.observacao like %s order by l.valorTotal desc",
				name, "'A%'"),
				Lancamento.class
		);
		List<Lancamento> r = (List<Lancamento>) q.getResultList();
		
		em.close();
		return r;
	}
	
	public int consulta_item12() {
		String name = this.getEntityName();
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery(
			String.format(
					"update %s l set l.observacao = concat(l.observacao, ' - Possuem mais que 10 itens') where size(l.itens) > 10",
			name)
		);
		
		em.joinTransaction();
		int updateCount = q.executeUpdate();
		
		em.flush();
		em.close();
		
		em.getTransaction().commit();
		
		return updateCount;
	}
}
