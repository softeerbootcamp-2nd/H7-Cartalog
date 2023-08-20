package softeer.wantcar.cartalog.estimate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.PendingHashTagMap;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SimilarityQueryRepositoryImpl implements SimilarityQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

}
