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

    protected static final String findEstimateInfoBydEstimateIds =
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
            "WHERE e.id IN (:estimateIds);";

    protected static final String findEstimateInfoWithSideImageBydEstimateIds =
            "SELECT " +
            "   e.id AS estimate_id, " +
            "   e.detail_trim_id AS detail_trim_id, " +
            "   t.name AS trim_name, " +
            "   t.min_price AS trim_price, " +
            "   mec.color_code AS exterior_color_code, " +
            "   tic.model_interior_color_code AS interior_color_code, " +
            "   mo.name AS model_type_name, " +
            "   mo.price AS model_type_price, " +
            "   mec.side_exterior_image_url AS exterior_car_side_image_url " +
            "FROM estimates AS e " +
            "JOIN detail_trims AS dt ON dt.id=e.detail_trim_id " +
            "JOIN trims AS t ON t.id=dt.trim_id " +
            "JOIN detail_model_decision_options AS dmdo ON dmdo.detail_model_id = dt.detail_model_id " +
            "JOIN model_options AS mo ON mo.id=dmdo.model_option_id " +
            "JOIN trim_exterior_colors AS tec ON tec.id=e.trim_exterior_color_id " +
            "JOIN model_exterior_colors AS mec ON mec.id=tec.model_exterior_color_id " +
            "JOIN trim_interior_colors AS tic ON tic.id=e.trim_interior_color_id " +
            "WHERE e.id IN (:estimateIds);";

    protected static final String findEstimateOptionsByEstimateIds =
            "SELECT " +
            "   e.id AS estimate_id, " +
            "   mo.id AS option_id, " +
            "   mo.name AS name, " +
            "   mo.price AS price, " +
            "   mo.image_url AS image_url " +
            "FROM estimates AS e " +
            "JOIN estimate_options AS eo ON eo.estimate_id=e.id " +
            "JOIN model_options AS mo ON mo.id=eo.model_option_id " +
            "WHERE e.id IN (:estimateIds)";

    protected static final String findEstimatePackagesByEstimateIds =
            "SELECT " +
            "   e.id AS estimate_id, " +
            "   mp.id AS option_id, " +
            "   mp.name AS name, " +
            "   mp.price AS price, " +
            "   mp.image_url AS image_url " +
            "FROM estimates AS e " +
            "JOIN estimate_packages AS ep ON ep.estimate_id=e.id " +
            "JOIN model_packages AS mp ON mp.id=ep.model_package_id " +
            "WHERE e.id IN (:estimateIds)";

    protected static final String findAveragePrice =
            "SELECT ROUND(COALESCE(AVG(estimates.price), 0)) AS average_price " +
            "FROM release_records " +
            "JOIN estimates ON estimates.id=release_records.estimate_id " +
            "JOIN detail_trims on detail_trims.id = estimates.detail_trim_id " +
            "AND detail_trims.trim_id = :trimId ";

    public static final String findEstimateCounts =
            "SELECT " +
            "   estimate_id, " +
            "   count(estimate_id) AS count, " +
            "   (SELECT price FROM estimates WHERE id=estimate_id) AS price " +
            "FROM release_records " +
            "WHERE estimate_id IN (:estimateIds) " +
            "GROUP BY estimate_id ";

    protected static final String findEstimateIdByEstimateDto =
            "SELECT estimates.id " +
            "FROM   estimates " +
            "       LEFT JOIN estimate_packages " +
            "              ON estimates.id = estimate_packages.estimate_id " +
            "       LEFT JOIN estimate_options " +
            "              ON estimates.id = estimate_options.estimate_id " +
            "WHERE  detail_trim_id = :detailTrimId " +
            "       AND estimates.trim_exterior_color_id = :trimExteriorColorId " +
            "       AND estimates.trim_interior_color_id = :trimInteriorColorId " +
            "GROUP  BY estimates.id " +
            "HAVING Count(DISTINCT model_package_id) = :countOfPackages " +
            "       AND Count(DISTINCT model_option_id) = :countOfOptions ";

    protected static final String findEstimateShareInfoByEstimateId =
            "SELECT detail_trims.trim_id                           AS trim_id, " +
            "       detail_trims.id                                AS detail_trim_id, " +
            "       detail_models.displacement                     AS displacement, " +
            "       detail_models.fuel_efficiency                  AS fuel_efficiency, " +
            "       model_exterior_colors.color_code               AS exterior_color_code, " +
            "       colors.name                                    AS exterior_color_name, " +
            "       model_exterior_colors.price                    AS exterior_color_price, " +
            "       colors.image_url                               AS exterior_color_image_url, " +
            "       trim_interior_colors.model_interior_color_code AS interior_color_code, " +
            "       model_interior_colors.name                     AS interior_color_name, " +
            "       model_interior_colors.price                    AS interior_color_price, " +
            "       model_interior_colors.image_url                AS interior_color_image_url, " +
            "       model_exterior_colors.side_exterior_image_url  AS exterior_car_side_image_url, " +
            "       model_interior_colors.interior_image_url       AS interior_car_image_url " +
            "FROM   estimates " +
            "       INNER JOIN detail_trims " +
            "               ON detail_trims.id = estimates.detail_trim_id " +
            "       INNER JOIN detail_models " +
            "               ON detail_models.id = detail_trims.detail_model_id " +
            "       INNER JOIN trim_exterior_colors " +
            "               ON trim_exterior_colors.id = estimates.trim_exterior_color_id " +
            "       INNER JOIN model_exterior_colors " +
            "               ON model_exterior_colors.id = " +
            "                  trim_exterior_colors.model_exterior_color_id " +
            "       INNER JOIN colors " +
            "               ON colors.code = model_exterior_colors.color_code " +
            "       INNER JOIN trim_interior_colors " +
            "               ON trim_interior_colors.id = estimates.trim_interior_color_id " +
            "       INNER JOIN model_interior_colors " +
            "               ON model_interior_colors.code = " +
            "                  trim_interior_colors.model_interior_color_code " +
            "WHERE  estimates.id = :estimateId ";


    protected static final String findEstimateModelOptionIdsByEstimateId =
            "SELECT model_options.id  " +
            "FROM   estimates  " +
            "       INNER JOIN detail_trims  " +
            "               ON detail_trims.id = estimates.detail_trim_id  " +
            "       INNER JOIN detail_models  " +
            "               ON detail_models.id = detail_trims.detail_model_id  " +
            "       INNER JOIN detail_model_decision_options  " +
            "               ON detail_model_decision_options.detail_model_id =  " +
            "                  detail_models.id  " +
            "       INNER JOIN model_options  " +
            "               ON model_options.id =  " +
            "                  detail_model_decision_options.model_option_id  " +
            "WHERE  estimates.id = :estimateId ";

    protected static final String getEstimatePrice =
            "SELECT " +
            "    t.min_price + " +
            "    SUM(mo.price) + " +
            "    (SELECT mec.price FROM trim_exterior_colors AS tec  " +
            "        JOIN model_exterior_colors AS mec " +
            "           ON mec.id = tec.model_exterior_color_id AND tec.id = :exteriorColorId) + " +
            "    (SELECT mic.price FROM trim_interior_colors AS tic  " +
            "        JOIN model_interior_colors AS mic " +
            "           ON mic.code = tic.model_interior_color_code " +
                    "       AND tic.trim_exterior_color_id = :exteriorColorId AND tic.id= :interiorColorId ) + " +
            "    IFNULL((SELECT SUM(mo.price) FROM model_options AS mo WHERE mo.id IN (:optionIds)), 0) + " +
            "    IFNULL((SELECT SUM(mp.price) FROM model_packages AS mp WHERE mp.id IN (:packageIds)), 0) AS price " +
            "FROM detail_trims AS dt " +
            "JOIN trims AS t " +
            "   ON t.id = dt.trim_id " +
            "JOIN detail_model_decision_options AS dmdo " +
            "   ON dmdo.detail_model_id = dt.detail_model_id " +
            "       AND dt.id = :detailTrimId " +
            "JOIN model_options AS mo " +
            "   ON mo.id=dmdo.model_option_id ";
}
