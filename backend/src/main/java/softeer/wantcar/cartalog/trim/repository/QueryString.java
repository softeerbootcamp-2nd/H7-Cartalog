package softeer.wantcar.cartalog.trim.repository;

public class QueryString {
    private QueryString() {
    }

    protected static final String findBasicModelByName =
            "SELECT id, name, category FROM basic_models WHERE name=:name ";

    protected static final String findTrimsByBasicModelId =
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
            "ON t.basic_model_id= :basicModelId ";

    protected static final String findDetailTrimInfoByTrimIdAndModelTypes =
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
            "WHERE dt.trim_id = :trimId ";

    protected static String findOptionsByDetailTrimId =
            "SELECT DISTINCT " +
            "   dto.id AS id, " +
            "   mo.name AS name, " +
            "   mo.parent_category AS parentCategory, " +
            "   mo.child_category AS childCategory, " +
            "   mo.image_url AS imageUrl, " +
            "   dto.price AS price, " +
            "   dto.basic AS basic, " +
            "   dto.color_condition AS colorCondition, " +
            "   dtoicc.trim_interior_color_id AS trimInteriorColorId, " +
            "   moht.hash_tag AS hashTag, " +
            "   hmg.model_option_id AS hmgModelOptionId " +

            "FROM detail_trim_options AS dto " +

            "JOIN model_options AS mo ON mo.id=dto.model_option_id " +
            "LEFT OUTER JOIN detail_trim_option_interior_color_condition AS dtoicc ON dtoicc.detail_trim_option_id=dto.id " +
            "LEFT OUTER JOIN model_option_hash_tags AS moht ON moht.model_option_id = dto.model_option_id " +
            "LEFT OUTER JOIN hmg_data AS hmg ON hmg.model_option_id=dto.model_option_id " +
            "WHERE visibility=true AND detail_trim_id= :detailTrimId  ";

    public static String findPackagesByTrimId =
            "SELECT DISTINCT " +
            "   dtp.id AS id, " +
            "   dtp.name AS name, " +
            "   dtp.parent_category AS parentCategory, " +
            "   dtp.color_condition AS colorCondition, " +
            "   dtp.image_url AS imageUrl, " +
            "   dtp.price AS price, " +
            "   dto.color_condition AS colorCondition, " +
            "   dtpicc.trim_interior_color_id AS trimInteriorColorId, " +
            "   pht.hash_tag AS hashTag, " +
            "   hmg.model_option_id AS hmgModelOptionId " +

            "FROM detail_trim_packages AS dtp " +

            "LEFT OUTER JOIN detail_trim_package_interior_color_condition AS dtpicc ON dtpicc.detail_trim_package_id=dtp.id " +
            "LEFT OUTER JOIN package_hash_tags AS pht ON pht.package_id=dtp.id " +
            "JOIN trim_package_options AS tpo ON tpo.trim_package_id=dtp.id " +
            "JOIN detail_trim_options AS dto ON dto.id=tpo.detail_trim_option_id " +
            "JOIN hmg_data AS hmg ON hmg.model_option_id=dto.model_option_id " +
            "WHERE dtp.detail_trim_id= :detailTrimId ";

    protected static final String findTrimExteriorColorByTrimId =
            "SELECT " +
            "  code, " +
            "  name, " +
            "  image_url, " +
            "  price, " +
            "  exterior_image_directory " +
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

    protected static final String findTrimInteriorColorByTrimIdAndExteriorColorCode =
            "SELECT " +
            "  model_interior_colors.code, " +
            "  model_interior_colors.name, " +
            "  model_interior_colors.price, " +
            "  model_interior_colors.image_url, " +
            "  model_interior_colors.interior_image_url " +
            "FROM model_interior_colors " +
            "INNER JOIN trim_interior_colors " +
            "ON model_interior_colors.code = trim_interior_colors.model_interior_color_code " +
            "WHERE trim_exterior_color_id = " +
            "  (SELECT trim_exterior_colors.id " +
            "  FROM trim_exterior_colors " +
            "  INNER JOIN model_exterior_colors " +
            "  ON trim_exterior_colors.model_exterior_color_id = model_exterior_colors.id " +
            "  WHERE trim_exterior_colors.trim_id = :trimId " +
            "    AND model_exterior_colors.color_code = :colorCode);";

    protected static final String findMultipleSelectableCategories =
            "SELECT " +
            "   category " +
            "FROM model_option_parent_categories " +
            "WHERE multiple_select=true";
}
