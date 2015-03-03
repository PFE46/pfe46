package webapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webapi.entities.Data;

/**
 * This interface extends CrudRepository<Document, String>. We have access to operation like :
 *  - save(Document entity)
 *  - findOne(String id)
 *  - findAll(Iterable<String> ids)
 */
public interface DataRepository extends CrudRepository<Data, String> {
}
