package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@RequiredArgsConstructor
@Transactional
public class SimilarityCommandRepositoryImpl implements SimilarityCommandRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

}
