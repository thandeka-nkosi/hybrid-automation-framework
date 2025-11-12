package core.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;


@Sources({"system:properties", "system:env"})
public interface AppConfig extends Config {

    @Key("baseUrl") @DefaultValue("https://www.saucedemo.com/")
    String baseUrl();

    @Key("browser") @DefaultValue("chrome")  // chrome | edge
    String browser();

    @Key("headless") @DefaultValue("false")
    boolean headless();

    @Key("timeoutSec") @DefaultValue("10")
    int timeoutSec();

    @Key("gridUrl") @DefaultValue("")
    String gridUrl();
}


