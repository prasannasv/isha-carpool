package org.ishausa.transport.carpool.renderer;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tosri on 12/29/2016.
 */
public class SoyRenderer {
    public enum CarPoolAppTemplate {
        LOGIN,
        MAIN;

        @Override
        public String toString() {
            return "." + name().toLowerCase();
        }
    }

    public static final SoyRenderer INSTANCE = new SoyRenderer();

    private final SoyTofu serviceTofu;

    private SoyRenderer() {
        final SoyFileSet sfs = SoyFileSet.builder()
                .add(new File("./src/main/webapp/template/carpool_app.soy"))
                .build();
        serviceTofu = sfs.compileToTofu().forNamespace("org.ishausa.transport.carpool.app");
    }

    public String render(final CarPoolAppTemplate template) {
        return render(template, new HashMap<>());
    }

    public String render(final CarPoolAppTemplate template, final Map<String, ?> data) {
        return serviceTofu.newRenderer(template.toString()).setData(data).render();
    }

}
