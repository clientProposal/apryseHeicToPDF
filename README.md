__Quick Setup__

Using VSC

1. Cmd + shift + p
2. Java: Create Java Project
3. Maven
4. maven-archetype-quickstart
5. Version
6. Group ID (com.poc)
7. Project name (java_for_poc)
8. Please use javac -version to check your java version and change maven.compiler.source and maven.compiler.target to match
9. "touch run.sh" on the same level as the pom.xml
11. echo "mvn clean compile exec:java -Dexec.mainClass="com.poc.App" > path/to/run.sh (change package/class)
12.  chmod +x run.sh
13. ./run.sh to try everything

__Setup PDFTron__

14. Add this right after opening "project" node in your POM file:

```xml
  <repositories>
    <repository>
      <id>pdftron</id>
      <name>PDFNet Maven</name>
      <url>https://pdftron.com/maven/release</url>
    </repository>
  </repositories>
  <modelVersion>4.0.0</modelVersion>
```

15. Add this to your dependencies:

```xml
    <dependency>
      <groupId>com.pdftron</groupId>
      <artifactId>PDFNet</artifactId>
      <version>10.10.0</version>
    </dependency>
    <dependency>
      <groupId>io.github.cdimascio</groupId>
      <artifactId>dotenv-java</artifactId>
      <version>3.0.0</version> 
  </dependency>
```

16. Add this to your main file to confirm everything links up:

```java
package com.poc;
import com.pdftron.pdf.*;

public class App {
    public static void main(String[] args) {
        try {
            PDFNet.initialize("xxxxx-your-key-here-xxxxxx");
            PDFDoc doc = new PDFDoc();
            doc.pageInsert(doc.getPageIterator(0), doc.pageCreate());
            System.out.println("Number of pages: " + doc.getPageCount());
            doc.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            PDFNet.terminate();
        }
    }
}
```

17. General files that may prove of importance:
src/main/resources, if you need it.
lib
.env 
.gitignore

.env in code:

```java
import io.github.cdimascio.dotenv.Dotenv;

Dotenv dotenv = Dotenv.configure()
  .directory("./")
  .ignoreIfMissing()
  .load();

String token = dotenv.get("PDFTRON_KEY");

```

18. Download the files seen in lib on this page, under Advanced Imaging Module:
https://dev.apryse.com/