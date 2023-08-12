package softeer.wantcar.cartalog.trim.repository;

public class QueryString {
    private QueryString() {
    }

    public static final String findBasicModelByName =
            "SELECT id, name, category FROM basic_models WHERE name=:name";

    public static final String findTrimsByBasicModelId =
            "SELECT  " +
            "(SELECT name FROM basic_models where id= :basicModelId) AS modelName,  " +
            "t.id AS trimId,  " +
            "t.name AS trimName,  " +
            "t.description AS description,  " +
            "t.min_price AS minPrice,  " +
            "t.max_price AS maxPrice,  " +
            "t.exterior_image_url AS exteriorImageUrl,  " +
            "t.interior_image_url AS interiorImageUrl,  " +
            "t.wheel_image_url AS wheelImageUrl,  " +
            "tb.name AS hmgName,  " +
            "tb.val AS hmgVal,  " +
            "tb.measure AS hmgMeasure,  " +
            "tb.unit AS hmgUnit,  " +
            "mt.child_category AS defaultModelTypeChildCategory,  " +
            "mt.id AS defaultModelTypeOptionId,  " +
            "mt.name AS defaultModelTypeOptionName,  " +
            "mt.price AS defaultModelTypeOptionPrice,  " +
            "eic.model_exterior_color_code AS defaultExteriorColorCode,  " +
            "eic.model_interior_color_code AS defaultInteriorColorCode  " +

            "FROM trims AS t  " +

            "LEFT OUTER JOIN  " +
            "(  " +
            "  SELECT sub_tb.trim_id, (SELECT name FROM model_options WHERE id=sub_tb.model_option_id) AS name, sub_tb.val, sub_tb.measure, sub_tb.unit  " +
            "  FROM (  " +
            "    SELECT DISTINCT trim_id, dto.model_option_id, val, measure, unit  " +
            "    FROM hmg_data AS hmg  " +
            "    JOIN detail_trim_options AS dto ON hmg.model_option_id = dto.model_option_id  " +
            "    JOIN detail_trims AS dt ON dto.detail_trim_id = dt.id  " +
            "    WHERE measure='15,000km 당' AND trim_id IN ( select id from trims where basic_model_id= :basicModelId)  " +
            "  ) AS sub_tb  " +
            "  ORDER BY sub_tb.trim_id, sub_tb.val desc  " +
            ") AS tb  " +
            "ON t.id=tb.trim_id  " +

            "JOIN  " +
            "(  " +
            "  SELECT  " +
            "  trim_id,  " +
            "  color_code as model_exterior_color_code,  " +
            "  model_interior_color_code,  " +
            "  price  " +
            "  FROM  " +
            "  (  " +
            "    SELECT trim_id, mec.color_code, model_exterior_color_id, model_interior_color_code, mec.price,  " +
            "    ROW_NUMBER() OVER (PARTITION BY trim_id) AS row_num  " +
            "    FROM trim_exterior_colors AS ec  " +
            "    JOIN trim_interior_colors AS ic ON ec.id=ic.trim_exterior_color_id  " +
            "    JOIN model_exterior_colors AS mec ON mec.id=model_exterior_color_id  " +
            "    WHERE mec.price=0  " +
            "  ) AS eic  " +
            "  WHERE row_num=1  " +
            ") AS eic  " +
            "ON t.id=eic.trim_id  " +

            "JOIN  " +
            "(  " +
            "  SELECT child_category, id, name, price_if_model_type_option AS price  " +
            "  FROM  " +
            "  (  " +
            "    SELECT *, ROW_NUMBER() OVER (PARTITION BY child_category ORDER BY id) AS row_num  " +
            "    FROM model_options  " +
            "    WHERE id IN  " +
            "    (  " +
            "      SELECT distinct model_option_id  " +
            "      FROM detail_model_decision_options  " +
            "      WHERE detail_model_id IN  " +
            "        (SELECT id FROM detail_models WHERE basic_model_id= :basicModelId)  " +
            "    ) " +
            "  ) AS mt" +
            ") AS mt  " +
            "ON t.basic_model_id= :basicModelId;";

    public static final String findDetailTrimInfoByTrimIdAndModelTypes =
            "SELECT " +
            "  dt.id AS detailTrimId, " +
            "  dm.displacement AS displacement, " +
            "  dm.fuel_efficiency AS fuelEfficiency " +
            "FROM detail_trims AS dt " +
            "JOIN " +
            "  ( " +
            "    SELECT dm.id, dm.displacement, dm.fuel_efficiency " +
            "    FROM detail_models AS dm " +
            "    JOIN detail_model_decision_options AS dmdo ON dm.id = dmdo.detail_model_id " +
            "    WHERE dmdo.model_option_id IN (:modelTypeIds) " +
            "    GROUP BY dm.id " +
            "    HAVING COUNT(DISTINCT dmdo.model_option_id) = :modelTypeCount " +
            ") AS dm ON dt.detail_model_id=dm.id " +
            "WHERE dt.trim_id = :trimId;";

    protected static final String findTrimExteriorColorByTrimId =
            "SELECT " +
            "  code, " +
            "  name, " +
            "  image_url, " +
            "  price, " +
            "  exterior_image_url " +
            "FROM trim_exterior_colors " +
            "INNER JOIN model_exterior_colors " +
            "ON trim_exterior_colors.model_exterior_color_id = model_exterior_colors.id " +
            "INNER JOIN colors " +
            "  ON model_exterior_colors.color_code = colors.code " +
            "WHERE trim_id = :trimId";

    protected static final String findTrimIdByModelNameAndTrimName =
            "SELECT trims.id " +
            "FROM       basic_models " +
            "INNER JOIN trims " +
            "where      basic_models.name = :modelName " +
            "AND        trims.name = :trimName";
}
