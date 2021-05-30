package api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceProviderDesc {

    public String host;
    public Integer port;
    public String serviceClass;

}
