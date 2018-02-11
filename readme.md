# deors-demos-testing-htmlunit

Demonstration of Java UI integration tests with HtmlUnit on top of JUnit.

The initial configuration of the project, in pom.xml and test code, is set to work without needing any external dependency, by combining Cargo to provision a Tomcat runtime and of course HtmlUnit to run tests. To launch the complete cycle just execute:

	mvn clean verify

The test cases are prepared to receive certain external configuration items, to control its behaviour. The list below shows the set of environment variables (in uppercase, separated by '_') and JVM system properties (in lowercase, separated by '.'), that can be used to control test execution:

* `TARGET_SERVER_URL` / `test.target.server.url` -> URL where the application is listening to HTTP requests. Default value is: `http://localhost:56080/deors-demos-testing-htmlunit` (the application as is launched by Cargo in a Tomcat runtime).
