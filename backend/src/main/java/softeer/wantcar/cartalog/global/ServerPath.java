package softeer.wantcar.cartalog.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import softeer.wantcar.cartalog.global.utils.RowMapperUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServerPath {
    @Value("${env.imageServerPath}")
    public String IMAGE_SERVER_PATH = "example-url";

    private final RowMapperUtils.RowMapperStrategy rowMapperStrategy = new RowMapperUtils.RowMapperStrategy() {
        @Override
        public boolean isMappingTarget(Class<?> type, String name) {
            return name.endsWith("image_url") && type == String.class;
        }

        @Override
        public Object mapping(String name, ResultSet rs) throws SQLException {
            return attachImageServerPath(rs.getString(name));
        }
    };

    public String attachImageServerPath(String url) {
        return IMAGE_SERVER_PATH + "/" + url;
    }

    public RowMapperUtils.RowMapperStrategy getImageServerPathRowMapperStrategy() {
        return rowMapperStrategy;
    }
}
