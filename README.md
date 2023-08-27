# Customer Apps Getting Started

### Instalation Guides

The following steps are the way to run the backend application

### Validate source tree

    ./mvnw clean install

For the first time run, it would be take time to download pom (lib) files needed.

### Start Using Internal Tomcat

    ./mvnw -P dev spring-boot:run

### Starter Mode

You need to edit `pom.xml` to change the properties which one you will use

    <profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <activatedProperties>dev</activatedProperties>
            </properties>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <activatedProperties>prod</activatedProperties>
            </properties>
        </profile>
    </profiles>

