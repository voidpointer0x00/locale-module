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
    <version>2.0</version>
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
  implementation 'com.github.NyanGuyMF:locale-module:2.0'
}
```

### Basic API

```java
Locale locale = new LocaleConfigurationSection(this, getConfig().getConfigurationSection("messages"));
locale.localize("greetings.hi-console", "&aLocale for &6{plugin}&e is loaded!")
        .set("plugin", getName())
        .send(Bukkit.getConsoleSender());
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
