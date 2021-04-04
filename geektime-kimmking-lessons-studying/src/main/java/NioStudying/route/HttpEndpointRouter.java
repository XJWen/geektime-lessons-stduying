package NioStudying.route;

import java.util.List;

public interface HttpEndpointRouter {

    String router(List<String> endpoints);
}
