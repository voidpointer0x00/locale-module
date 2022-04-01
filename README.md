<div align=center>
  <h1>The Locale Module</h1>
</div>
<div align=center>

[![JitPack][JitPackBadge]][JitPackUrl]
[![Tests][TestsBadge]][TestsUrl]
[![CodeQL][CodeQLBadge]][CodeQLUrl]
[![WTFPL][LicenseBadge]](LICENSE)

## [Wiki][WikiUrl]

</div>

Locale Module is a simple library for Spigot plugins that allows you to quickly set
up configurable and easy-to-use i18n, so that you don't have to worry about managing
color translations, string interpolation *(variables inside strings)*, files, YamlConfigurations and [Chat Components][TextComponentAPIUrl].

Make sure to check out [2.0 Rich JSON update][JsonUpdateUrl] for configuration and in game message showcase :)

## Quick start

### Maven setup
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.NyanGuyMF</groupId>
    <artifactId>locale-module</artifactId>
    <version>3.1.1</version>
  </dependency>
</dependencies>
```
### Grade setup
```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.NyanGuyMF:locale-module:3.1.1'
}
```

### Quick start

```java
public final class MyPlugin extends JavaPlugin {
    @PluginLocale
    private static LocaleFileConfiguration locale;

    @Override public void onLoad() {
        LocaleAnnotationResolver.resolve(this);
    }

    @Override public void onEnable() {
        locale.localize("messages.plugin-enabled", "&ePlugin &b{plugin}&e enabled!")
                .set("plugin", getName())
                .send(Bukkit.getConsoleSender());
    }

    @Override public void onDisable() {
        locale.localize("messages.plugin-disabled", "&ePlugin &b{plugin}&e disabled!")
                .set("plugin", getName())
                .send(Bukkit.getConsoleSender());
    }
}
```

## Check out [Wiki][WikiUrl]!

[TextComponentAPIUrl]: https://www.spigotmc.org/wiki/the-chat-component-api/
[JsonUpdateUrl]: https://github.com/NyanGuyMF/locale-module/releases/tag/2.0
[WikiUrl]: https://github.com/NyanGuyMF/locale-module/wiki

[JitPackBadge]: https://jitpack.io/v/NyanGuyMF/locale-module.svg
[TestsBadge]: https://github.com/NyanGuyMF/locale-module/actions/workflows/tests.yml/badge.svg
[CodeQLBadge]: https://github.com/NyanGuyMF/locale-module/actions/workflows/codeql.yml/badge.svg
[LicenseBadge]: https://img.shields.io/github/license/NyanGuyMF/locale-module.svg

[JitPackUrl]: https://jitpack.io/#NyanGuyMF/locale-module
[TestsUrl]: https://github.com/NyanGuyMF/locale-module/actions/workflows/tests.yml
[CodeQLUrl]: https://github.com/NyanGuyMF/locale-module/actions/workflows/codeql.yml
