package com.sc.td.business.dao.scgroup.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.sc.td.business.dao.scgroup.ScGroupDao;
import com.sc.td.business.entity.scgroup.ScGroup;

@Repository("scGroupDaoImpl")
public class ScGroupDaoImpl implements ScGroupDao {

	private EntityManager em;

	@PersistenceContext
	public void SetEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<ScGroup> findAll() {

		return null;
	}

	@Override
	public List<ScGroup> findAll(Sort sort) {

		return null;
	}

	@Override
	public List<ScGroup> findAll(Iterable<String> ids) {

		return null;
	}

	@Override
	public <S extends ScGroup> List<S> save(Iterable<S> entities) {

		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends ScGroup> S saveAndFlush(S entity) {

		return null;
	}

	@Override
	public void deleteInBatch(Iterable<ScGroup> entities) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public ScGroup getOne(String id) {

		return null;
	}

	@Override
	public Page<ScGroup> findAll(Pageable pageable) {

		return null;
	}

	@Override
	public <S extends ScGroup> S save(S entity) {

		return null;
	}

	@Override
	public ScGroup findOne(String id) {

		return null;
	}

	@Override
	public boolean exists(String id) {

		return false;
	}

	@Override
	public long count() {

		return 0;
	}

	@Override
	public void delete(String id) {

	}

	@Override
	public void delete(ScGroup entity) {

	}

	@Override
	public void delete(Iterable<? extends ScGroup> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScGroup> findGroupRankDESC(List<Object> paramList) {
		List<ScGroup> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select t1.group_id,t1.group_name,t1.user_id,IFNULL(cast(t1.pk_win/t1.pk_total as decimal(18,2)),0) as win_rate,t1.group_type,t1.pk_total,t1.pk_win,t1.pk_row_win");
			sb.append(
					",t1.accept_pk_times,t1.is_allow_pk,t1.create_by,t1.create_date,t1.update_by,t1.update_date,t2.user_name,t2.image ");
			sb.append(" from sc_group t1 left join sc_user t2 on t1.user_id=t2.user_id  ");
			sb.append("where 1=1 ");
			sb.append("and (IFNULL(t1.pk_win/t1.pk_total,0) between ?1 and ?2 ) ");
			sb.append("and t1.pk_row_win BETWEEN ?3 and ?4 ");
			sb.append("and t1.pk_total BETWEEN ?5 and ?6 ");
			sb.append("and t1.accept_pk_times<?7 ");
			sb.append("and t1.group_name like ?8 ");
			sb.append("group by t1.group_id ");
			sb.append("order by (t1.pk_win/t1.pk_total) DESC ");
			sb.append("limit ?9,?10 ");

			Query query = em.createNativeQuery(sb.toString(), ScGroup.class);
			for (int i = 0; i < paramList.size(); i++) {
				if (i == 7) {
					query.setParameter(i + 1, "%" + paramList.get(i) + "%");
				} else {
					query.setParameter(i + 1, paramList.get(i));
				}
			}
			list = query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return list;
	}

}
