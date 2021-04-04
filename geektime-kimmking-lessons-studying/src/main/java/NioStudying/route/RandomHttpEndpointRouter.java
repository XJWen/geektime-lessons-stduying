package NioStudying.route;

import java.security.SecureRandom;
import java.util.List;

public class RandomHttpEndpointRouter implements HttpEndpointRouter{
    @Override
    public String router(List<String> urlList) {
        int urlListSize = urlList.size();
        SecureRandom random = new SecureRandom();
        return urlList.get(random.nextInt(urlListSize));
    }
}
