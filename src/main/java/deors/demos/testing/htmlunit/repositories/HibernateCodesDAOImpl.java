package deors.demos.testing.htmlunit.repositories;

import java.util.Collection;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import deors.demos.testing.htmlunit.entities.Codes;
import deors.demos.testing.htmlunit.entities.CodesImpl;

/**
 * The Codes data access object implementation using Hibernate.
 *
 * @author jorge.hidalgo
 * @version 1.0
 */
@Repository("codesDAO")
public class HibernateCodesDAOImpl
    extends HibernateTemplate
    implements CodesDAO {

    /**
     * Default constructor.
     */
    public HibernateCodesDAOImpl() {

        super();
    }

    /**
     * Returns all Codes records.
     *
     * @return all records
     */
    public Collection<? extends Codes> selectAll() {

        return loadAll(CodesImpl.class);
    }

    /**
     * Returns Codes record by the given 'code' value.
     *
     * @param criteria the bean containing the 'code' to be selected
     * @return the selected record
     */
    public Codes select(Codes criteria) {

        return (Codes) find("from CodesImpl where code=?", criteria.getCode()).get(0);
    }

    /**
     * Adds a new Codes record.
     *
     * @param record the new record
     * @return the just added record
     */
    public Codes insert(Codes record) {

        save(record);
        return record;
    }

    /**
     * Updates existing Codes records.
     *
     * @param record the record to be updated
     * @return number of records updated
     */
    public int update(Codes record) {

        super.update(record);
        return 1;
    }

    /**
     * Deletes existing Codes records.
     *
     * @param record the record to be deleted
     * @return number of records deleted
     */
    public int delete(Codes record) {

        super.delete(record);
        return 1;
    }
}
