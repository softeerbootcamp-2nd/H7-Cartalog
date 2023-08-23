package softeer.wantcar.cartalog.similarity.repository;


public class QueryString {
    private QueryString() {
    }

    protected static final String findHashTagKey =
            "SELECT " +
            "   idx " +
            "FROM pending_hash_tag_similarities " +
            "WHERE trim_id= :trimId AND hash_tag_key= :hashTagKey ";

    protected static final String findPendingHashTagKeys =
            "SELECT " +
            "   idx, " +
            "   hash_tag_key " +
            "FROM pending_hash_tag_similarities " +
            "WHERE trim_id= :trimId AND idx > " +
            "   (" +
            "       SELECT " +
            "           last_calculated_index " +
            "       FROM pending_hash_tag_similarities " +
            "       WHERE hash_tag_key= :hashTagKey AND trim_id= :trimId" +
            "   ) ";

    protected static final String findSimilarities =
            "SELECT " +
            "   target_hash_tag_index AS idx , " +
            "   similarity " +
            "FROM hash_tag_similarities " +
            "WHERE origin_hash_tag_index = " +
            "   ( " +
            "       SELECT " +
            "           idx " +
            "       FROM pending_hash_tag_similarities " +
            "       WHERE trim_id= :trimId AND " +
            "           hash_tag_key= :hashTagKey " +
            "   )";

    protected static final String findSimilarEstimateIds =
            "SELECT MIN(estimate_id) " +
            "FROM similar_estimates " +
            "WHERE hash_tag_index IN ( :hashTagIndexes ) " +
            "GROUP BY hash_tag_index ";

    protected static final String updateLastCalculatedIndex =
            "UPDATE pending_hash_tag_similarities " +
            "   SET last_calculated_index= :lastCalculatedIndex " +
            "WHERE trim_id= :trimId AND hash_tag_key= :hashTagKey ";

    protected static final String deleteSimilarities =
            "DELETE FROM hash_tag_similarities " +
            "WHERE origin_hash_tag_index= " +
            "   (" +
            "       SELECT " +
            "           idx " +
            "       FROM pending_hash_tag_similarities " +
            "       WHERE trim_id= :trimId AND hash_tag_key= :hashTagKey" +
            "   )";

    protected static final String saveSimilarities =
            "INSERT INTO hash_tag_similarities (target_hash_tag_index, similarity, origin_hash_tag_index) " +
            "VALUES (:targetHashTagIndex, :similarity, " +
            "   (SELECT idx FROM pending_hash_tag_similarities WHERE trim_id= :trimId AND hash_tag_key= :hashTagKey)) ";

    protected static final String saveHashTagKey =
            "INSERT INTO pending_hash_tag_similarities (hash_tag_key, trim_id, last_calculated_index) " +
            "VALUES (:hashTagKey, :trimId, :lastCalculatedIndex) ";

    protected static final String saveSimilarEstimate =
            "INSERT INTO similar_estimates " +
            "(estimate_id, hash_tag_index) VALUES " +
            "(:estimateId, " +
            "   (SELECT " +
            "       idx " +
            "   FROM pending_hash_tag_similarities " +
            "   WHERE trim_id= :trimId AND hash_tag_key= :hashTagKey)) ";

    protected static final String findAllHashTagKeys =
            "SELECT " +
            "   idx, " +
            "   hash_tag_key " +
            "FROM pending_hash_tag_similarities " +
            "WHERE trim_id= :trimId ";
}
