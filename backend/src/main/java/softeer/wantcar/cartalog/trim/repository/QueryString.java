package softeer.wantcar.cartalog.trim.repository;

public class QueryString {
    private QueryString() {
    }

    public static final String findTrimsByBasicModelIdQuery =
            "SELECT " +
            "(SELECT name FROM basic_models where id= ?) AS model_name," +
            "t.id, t.name, t.description, t.min_price, t.max_price, t.image_url," +
            "tb.name, tb.`value`, tb.measure," +
            "mt.name, mt.child_category, " +
            "eic.model_exterior_color_id, eic.model_interior_color_id " +
            "FROM trims AS t " +
            "LEFT OUTER JOIN " +
            " ( " +
            "  SELECT tb.trim_id, tb.name, tb.`value`, tb.measure " +
            "  FROM ( " +
            "    SELECT trim_id, name, `value`, measure, " +
            "      ROW_NUMBER() OVER(PARTITION BY trim_id ORDER BY `value` DESC) AS row_num " +
            "    FROM ( " +
            "      SELECT DISTINCT trim_id, name, `value`, measure " +
            "      FROM hmg_data AS hmg " +
            "      JOIN detail_trim_options AS dto ON hmg.model_option_id = dto.model_option_id " +
            "      JOIN detail_trims AS dt ON dto.detail_trim_id = dt.id " +
            "      WHERE trim_id in ( select id from trims where basic_model_id= ? ) " +
            "    ) AS sub_tb " +
            "  ) AS tb " +
            "  WHERE tb.row_num <= 3 " +
            "  ORDER BY tb.trim_id, tb.`value` desc " +
            ") AS tb " +
            "ON t.id=tb.trim_id " +
            "JOIN " +
            "( " +
            "  SELECT trim_id, (SELECT color_id FROM model_exterior_colors WHERE id = model_exterior_color_id) AS model_exterior_color_id, model_interior_color_id " +
            "  FROM " +
            "  ( " +
            "    SELECT trim_id, model_exterior_color_id, model_interior_color_id, ROW_NUMBER() OVER (PARTITION BY trim_id) AS row_num " +
            "    FROM trim_exterior_colors AS ec " +
            "    JOIN trim_interior_colors AS ic ON ec.id=ic.trim_exterior_color_id " +
            "  ) " +
            "  WHERE row_num=1 " +
            ") AS eic " +
            "ON t.id=eic.trim_id " +
            "JOIN " +
            "( " +
            "  SELECT name, child_category, image_url " +
            "  FROM " +
            "  ( " +
            "    SELECT *, ROW_NUMBER() OVER (PARTITION BY CHILD_CATEGORY ORDER BY ID) AS row_num " +
            "    FROM model_options " +
            "    WHERE id in " +
            "    ( " +
            "      SELECT distinct model_option_id " +
            "      FROM DETAIL_MODEL_DECISION_OPTIONS " +
            "      WHERE detail_model_id in " +
            "        (SELECT id FROM detail_models WHERE basic_model_id= ?) " +
            "    ) " +
            "  ) " +
            "  WHERE row_num=1 " +
            ") AS mt " +
            "WHERE t.basic_model_id= ?; ";
}
