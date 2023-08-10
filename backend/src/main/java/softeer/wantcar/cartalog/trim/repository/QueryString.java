package softeer.wantcar.cartalog.trim.repository;

public class QueryString {
    private QueryString() {
    }

    public static final String findBasicModelByName =
            "SELECT id, name, category FROM basic_models WHERE name=?";

    public static final String findTrimsByBasicModelIdQuery =
            "SELECT " +
            "(SELECT name FROM basic_models where id= ?) AS modelName, " +
            "t.id AS trimId, " +
            "t.name AS trimName, " +
            "t.description AS description, " +
            "t.min_price AS minPrice, " +
            "t.max_price AS maxPrice, " +
            "t.exterior_image_url AS exteriorImageUrl, " +
            "t.interior_image_url AS interiorImageUrl, " +
            "t.wheel_image_url AS wheelImageUrl, " +
            "tb.name AS hmgName, " +
            "tb.val AS hmgVal, " +
            "tb.measure AS hmgMeasure, " +
            "tb.unit AS hmgUnit, " +
            "mt.child_category AS defaultModelTypeChildCategory, " +
            "mt.id AS defaultModelTypeOptionId, " +
            "mt.name AS defaultModelTypeOptionName, " +
            "mt.price AS defaultModelTypeOptionPrice, " +
            "eic.model_exterior_color_code AS defaultExteriorColorCode, " +
            "eic.model_interior_color_code AS defaultInteriorColorCode " +

            "FROM trims AS t " +

            "LEFT OUTER JOIN " +
            "( " +
            "  SELECT tb.trim_id, tb.name, tb.val, tb.measure, tb.unit " +
            "  FROM ( " +
            "    SELECT trim_id, name, val, measure, unit, " +
            "      ROW_NUMBER() OVER(PARTITION BY trim_id ORDER BY val DESC) AS row_num " +
            "    FROM ( " +
            "      SELECT DISTINCT trim_id, name, val, measure, unit " +
            "      FROM hmg_data AS hmg " +
            "      JOIN detail_trim_options AS dto ON hmg.model_option_id = dto.model_option_id " +
            "      JOIN detail_trims AS dt ON dto.detail_trim_id = dt.id " +
            "      WHERE trim_id IN ( select id from trims where basic_model_id= ? ) " +
            "    ) AS sub_tb " +
            "  ) AS tb " +
            "  WHERE tb.row_num <= 3 " +
            "  ORDER BY tb.trim_id, tb.val desc " +
            ") AS tb " +
            "ON t.id=tb.trim_id " +

            "JOIN " +
            "( " +
            "  SELECT " +
            "  trim_id, " +
            "  color_code as model_exterior_color_code, " +
            "  model_interior_color_code, " +
            "  price " +
            "  FROM " +
            "  ( " +
            "    SELECT trim_id, mec.color_code, model_exterior_color_id, model_interior_color_code, mec.price, " +
            "    ROW_NUMBER() OVER (PARTITION BY trim_id) AS row_num " +
            "    FROM trim_exterior_colors AS ec " +
            "    JOIN trim_interior_colors AS ic ON ec.id=ic.trim_exterior_color_id " +
            "    JOIN model_exterior_colors AS mec ON mec.id=model_exterior_color_id " +
            "    WHERE mec.price=0 " +
            "  ) " +
            "  WHERE row_num=1 " +
            ") AS eic " +
            "ON t.id=eic.trim_id " +

            "JOIN " +
            "( " +
            "  SELECT child_category, id ,name, price_if_model_type_option AS price " +
            "  FROM " +
            "  ( " +
            "    SELECT *, ROW_NUMBER() OVER (PARTITION BY child_category ORDER BY id) AS row_num " +
            "    FROM model_options " +
            "    WHERE id IN " +
            "    ( " +
            "      SELECT distinct model_option_id " +
            "      FROM detail_model_decision_options " +
            "      WHERE detail_model_id IN " +
            "        (SELECT id FROM detail_models WHERE basic_model_id= ?) " +
            "    ) " +
            "  ) " +
            "  WHERE row_num=1 " +
            ") AS mt " +
            "WHERE t.basic_model_id= ?; ";
}
