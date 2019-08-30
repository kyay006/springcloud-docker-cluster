import com.liu.springrbacmanage.SpringRbacManageApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * web容器中进行部署
 * 
 * @author wave
 */
public class WaterServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(SpringRbacManageApplication.class);
    }

}
