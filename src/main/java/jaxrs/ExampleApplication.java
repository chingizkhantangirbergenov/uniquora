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
        singletons.add(new GpaJaxService());
        singletons.add(new CompletedCoursesJaxService());
        singletons.add(new CurrentCoursesJaxService());
        singletons.add(new CoursesJaxService());
        singletons.add(new UsersJaxService());
        singletons.add(new FeedbackJaxService());
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
