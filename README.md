# How to prepare artifacts

Run these commands to prepare binaries and sources for web site:

```bash
mvn clean
mvn assembly:assembly
```

and optionally:

```bash
ant add.version
```

Artifacts will be located in target directory.


How to build updated help.jar

```bash
ant help.jar
```

# Links

* http://scriptlandia.blogspot.com/2007/11/tips-for-intellij-idea-plugins.html


