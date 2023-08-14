package softeer.wantcar.cartalog.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerPath {
    @Value("${env.imageServerPath}")
    public String IMAGE_SERVER_PATH = "example-url";

    public String attachImageServerPath(String url) {
        return IMAGE_SERVER_PATH + "/" + url;
    }
}
