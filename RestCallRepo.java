package kambu.rekrutacja;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface RestCallRepo extends CrudRepository<RestCall, Long> {
    List<String> findById(@Param("id") String id);
}
