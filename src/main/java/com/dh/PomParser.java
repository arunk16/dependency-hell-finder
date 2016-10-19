package com.dh;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class PomParser {

    private static void ramlStuff(String artifactId, Dependency dependency, Map<String, ServiceAttributes> services) {
        if (dependency != null && dependency.getClassifier() != null && dependency.getClassifier().equals("raml")) {
            services.get(artifactId).getDependencies().add(dependency.getArtifactId());
            // check if dependency exists as a service
            if (!services.containsKey(dependency.getArtifactId())) {
                services.put(dependency.getArtifactId(), new ServiceAttributes());
            }
        }
    }

    public static Map<String, ServiceAttributes> parsePom(Path pom, Map<String, ServiceAttributes> services) throws IOException, XmlPullParserException {

        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pom.toFile()));

        ServiceAttributes attributes = new ServiceAttributes();
        attributes.setVersion((model.getVersion() != null && !model.getVersion().isEmpty()) ? model.getVersion() : model.getParent().getVersion());
        services.put(model.getArtifactId(), attributes);

        Build build = model.getBuild();
        if (build != null) {
            build.getPlugins().forEach(plugin -> plugin.getDependencies()
                    .forEach(dependency -> ramlStuff(model.getArtifactId(), dependency, services)));
        }

        return services;
    }

    public static Map<String, ServiceAttributes> parseRaml(Path raml, Map<String, ServiceAttributes> services) throws IOException, XmlPullParserException {

        YamlConfig config = new YamlConfig();
        config.setClassTag("include", String.class);
        YamlReader reader = new YamlReader(new FileReader(raml.toFile()), config);

        Object object = reader.read();
//        System.out.println(object);
        Map map = (Map)object;
        System.out.println("BaseURI "  +   map.get("baseUri"));
        System.out.println("Keys "  +   map.keySet());
        System.out.println("/structure.handler.command "  +   map.get("/structure.handler.command"));

        YamlReader command = new YamlReader(map.get("/structure.handler.command").toString());
        Object object2 = command.read();
        Map map2 = (Map)object2;
        System.out.println("Keys2 "  +   map2.keySet());

        System.out.println("********* post key "  +   map2.get("post=").toString());

        YamlReader command3 = new YamlReader(map.get("post=").toString());
//        Object object3 = command3.read();
//        Map map3 = (Map)object3;


        System.out.println("********** Keys3  ");//  +   map3.keySet());
        return services;
    }
}
