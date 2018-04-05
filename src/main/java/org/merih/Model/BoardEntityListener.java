/**
 * 
 */
package org.merih.Model;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import org.merih.service.BeanUtil;

import static javax.transaction.Transactional.TxType.MANDATORY;
import static org.merih.Model.Action.*;


/**
 * @author Merih
 *
 */
public class BoardEntityListener {

	@PrePersist
	public void prePersist(Board target) {
		perform(target, INSERTED);
	}

	@PreUpdate
	public void preUpdate(Board target) {
		perform(target, UPDATED);
	}

	@PreRemove
	public void preRemove(Board target) {
		perform(target, DELETED);
	}

	@Transactional(MANDATORY)
	private void perform(Board target, Action action) {
		EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
		entityManager.persist(new BoardHistory(target, action));
	}

}
