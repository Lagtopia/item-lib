# ItemLib – A Modular Library for Streamlined Development

**ItemLib** is a modular utility library designed to simplify and standardize the development process across projects.

It is meant to ease some of the pains experienced with serializing and de-serializing NBT tags. It utilizes NBTI's API to achieve this.

---

## Features
- Modular item creation
- NBT serialization and de-serialization.
- Many robust ways to create custom items.

---

##  Installation

This project is automatically deployed and published to GitHub Packages, making it easy to integrate with your Maven-based project.

### 1. Add GitHub Packages to your `pom.xml`
```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/Lagtopia/item-lib</url>
  </repository>
</repositories>
```

### 2. Add ItemLib as a dependency to your `pom.xml`
```xml
<dependencies>
  <dependency>
    <groupId>me.csdad.lagtopia</groupId>
    <artifactId>itemlib</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

You can view available versions alongside changelogs on our [packages](https://github.com/Lagtopia/item-lib/packages/) page.

### Optional - Shade ItemLib into your Final JAR
This option will prevent missing dependency issues, but will increase the scope of your build.
```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.5.0</version>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
          <configuration>
            <createDependencyReducedPom>false</createDependencyReducedPom>
            <minimizeJar>true</minimizeJar>
            <relocations>
              <relocation>
                <pattern>me.csdad.lagtopia.itemlib</pattern>
                <shadedPattern>your.package.shaded.itemlib</shadedPattern>
              </relocation>
            </relocations>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

### Example Usage - Using API to quickly deserialize an itemstack and get NBT tags
```java
import me.csdad.lagtopia.itemlib.Items.ItemFactory;

public class Example {
    
    public Map<String, String> getNBTTags(ItemStack item) {
        
        // Create a new item factory, utilizing the overload
        // constructor to deserialize the data
        ItemFactory factory = new ItemFactory(item);

        /**
         * This can return a null object, blank object, or populated object.
         * Null checks should be applied.
         */
        return factory.getAllTags();
    }
}
```