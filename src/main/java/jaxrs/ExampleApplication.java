package jaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ExampleApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ExampleApplication() {
        singletons.add(new RegisterJaxService());
        singletons.add(new LoginJaxService());
        singletons.add(new CoursesJaxService());
        singletons.add(new SyllabusJaxService());
        singletons.add(new UserInfoJaxService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
