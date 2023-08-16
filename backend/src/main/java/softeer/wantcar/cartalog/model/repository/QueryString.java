package softeer.wantcar.cartalog.model.repository;

public class QueryString {
    private QueryString() {
    }

    protected static final String findByModelTypeOptions =
            "SELECT DISTINCT mo.id                         AS model_option_id, " +
            "                mo.name                       AS name, " +
            "                mo.child_category             AS child_category, " +
            "                mo.image_url                  AS image_url, " +
            "                mo.description                AS description, " +
            "                mo.price_if_model_type_option AS price, " +
            "                hmg.name                      AS hmg_data_name, " +
            "                hmg.val                       AS hmg_data_value, " +
            "                hmg.measure                   AS hmg_data_measure " +
            "FROM   detail_model_decision_options AS dmdo " +
            "       JOIN detail_models AS dm " +
            "         ON dm.id = dmdo.detail_model_id " +
            "       JOIN detail_trims AS dt " +
            "         ON dt.detail_model_id = dm.id " +
            "       JOIN model_options AS mo " +
            "         ON mo.id = dmdo.model_option_id " +
            "       LEFT OUTER JOIN hmg_data AS hmg " +
            "                    ON mo.id = hmg.model_option_id " +
            "WHERE  dt.trim_id = :trimId ";

    protected static final String findCarSideExteriorAndInteriorImage =
            "SELECT " +
            "mec.side_exterior_image_url, " +
            "mic.interior_image_url " +
            "FROM model_exterior_colors AS mec " +
            "JOIN model_interior_colors AS mic ON mic.code= :interiorColorCode " +
            "WHERE mec.color_code= :exteriorColorCode ";
}
