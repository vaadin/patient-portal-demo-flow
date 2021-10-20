# Patient Portal UI

This is the UI for the Patient Portal demo app made with Vaadin Flow using Polymer templates.
The flow-hello-world project has been used as a starting point for this project.

Build
======
The project has dependency to the <code>patient-portal-backend</code> backend module which is not available
as a binary maven artefact in a remote repository. So there are two ways to build the project:
1. Run maven with a custom local repository location : <code>mvn -Dmaven.repo.local=localrepo install</code>. 
The <code>localrepo</code> directory contains the <code>patient-portal-backend</code> artifact binaries.
1. Clone the [patient-portal-backend](https://github.com/vaadin/patient-portal-demo-backend/tree/master/patient-portal-backend) project and install the artifact locally.  

Run using
```mvn clean spring-boot:run```

Running on Mac
======
If you experience an exceptions like
`java.lang.NoClassDefFoundError: com/sun/jna/platform/mac/IOKitUtil`
please add `<jna.version>5.6.0</jna.version>` property to the pom.xml.
