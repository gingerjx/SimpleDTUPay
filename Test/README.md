This project shows a setup of a Java project for Cucumber and Mockito. It works together with both JUnit 4 and JUnit 5 and Java 8 and Java 11 (17). 

For using Cucumber in Eclipse, you want to install the Cucumber plugin. In Eclipse, installing the Cucumber plugin is offered to you once you try to open a file with extension .feature. After the plugin is installled and Eclipse is restarted, you can select the project and then choose in the right button menu "Configure::Convert to Cucumber Project ...". This allows you later in a feature file to jump directly to the corresponding step definitions.

When you use this project as a starting point, please remember to remove those feature files and classes that were just included for demonstration purposes.

**Regarding IntelliJ**, rebuild the project from the build menu after loading the example to ensure that IntelliJ finds the step definition classes.