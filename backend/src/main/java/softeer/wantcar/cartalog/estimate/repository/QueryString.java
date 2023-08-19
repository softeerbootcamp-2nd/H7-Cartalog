package softeer.wantcar.cartalog.estimate.repository;

public class QueryString {
    private QueryString() {
    }

    protected static final String findEstimateOptionIdsByEstimateId =
            "SELECT DISTINCT " +
            "   dt.trim_id AS trim_id, " +
            "   eo.model_option_id AS option_id, " +
            "   ep.model_package_id AS package_id " +
            "FROM estimates AS e " +
            "JOIN detail_trims AS dt ON dt.id=e.detail_trim_id " +
            "LEFT OUTER JOIN estimate_options AS eo ON eo.estimate_id = e.id " +
            "LEFT OUTER JOIN estimate_packages AS ep ON ep.estimate_id = e.id " +
            "WHERE e.id= :estimateId";

    protected static final String findPendingHashTagKey =
            "SELECT pending_hash_tag_left_key " +
            "FROM pending_hash_tag_similarities " +
            "WHERE hash_tag_key= :hashTagKey " +
            "   AND trim_id= :trimId ";

    protected static final String findCalculatedSimilarHashTagKeys =
            "SELECT " +
            "   hash_tag_left_key AS hash_tag_key, " +
            "FROM hash_tag_similarities " +
            "WHERE hash_tag_key= :hashTagKey " +
            "   AND trim_id= :trimId " +
            "   AND similarity BETWEEN 0.2 AND 0.9 " +
            "ORDER BY similarity DESC ";

    protected static final String findSimilarEstimateIds =
            "SELECT estimate_id " +
            "FROM similar_estimates " +
            "WHERE hash_tag_key IN (:hashTagKey) " +
            "   AND trim_id= :trimId " +
            "LIMIT 4";

    protected static final String findSimilarEstimateInfoByEstimateIds =
            "SELECT " +
            "   e.id AS estimate_id, " +
            "   e.detail_trim_id AS detail_trim_id, " +
            "   t.name AS trim_name, " +
            "   t.min_price AS trim_price, " +
            "   mec.color_code AS exterior_color_code, " +
            "   tic.model_interior_color_code AS interior_color_code, " +
            "   mo.name AS model_type_name, " +
            "   mo.price AS model_type_price " +
            "FROM estimates AS e " +
            "JOIN detail_trims AS dt ON dt.id=e.detail_trim_id " +
            "JOIN trims AS t ON t.id=dt.trim_id " +
            "JOIN detail_model_decision_options AS dmdo ON dmdo.detail_model_id = dt.detail_model_id " +
            "JOIN model_options AS mo ON mo.id=dmdo.model_option_id " +
            "JOIN trim_exterior_colors AS tec ON tec.id=e.trim_exterior_color_id " +
            "JOIN model_exterior_colors AS mec ON mec.id=tec.model_exterior_color_id " +
            "JOIN trim_interior_colors AS tic ON tic.id=e.trim_interior_color_id " +
            "WHERE e.id IN (:similarEstimateIds);";

    protected static final String findSimilarEstimateOptionsByEstimateIds =
            "SELECT " +
            "   e.id AS estimate_id, " +
            "   mo.id AS option_id, " +
            "   mo.name AS name, " +
            "   mo.price AS price, " +
            "   mo.image_url AS image_url " +
            "FROM estimates AS e " +
            "JOIN estimate_options AS eo ON eo.estimate_id=e.id " +
            "JOIN model_options AS mo ON mo.id=eo.model_option_id " +
            "WHERE e.id IN (:similarEstimateIds)";

    protected static final String findSimilarEstimatePackagesByEstimateIds =
            "SELECT " +
            "   e.id AS estimate_id, " +
            "   mp.id AS option_id, " +
            "   mp.name AS name, " +
            "   mp.price AS price, " +
            "   mp.image_url AS image_url " +
            "FROM estimates AS e " +
            "JOIN estimate_packages AS ep ON ep.estimate_id=e.id " +
            "JOIN model_packages AS mp ON mp.id=ep.model_package_id " +
            "WHERE e.id IN (:similarEstimateIds)";
}
