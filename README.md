# `javason` - A simple json parser and serialization library

`javason` is a simple json parser and serialization library for the jvm platform (java 21 and up).

## Getting started

This project uses the `maven` build system for compilation, testing, packaging, etc.

### Installation

As a dependency from maven central, put this in your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.hacktheoxidation</groupId>
    <artifactId>javason</artifactId>
    <version>0.1.1</version>
</dependency>
```

### Development

Clone this repository:
```shell
git clone https://github.com/HackTheOxidation/javason
cd javason
```

Since this project uses `maven`, everything related to building and testing revolves around the `mvn` command.

E.g., to build the project:
```shell
mvn clean compile
```

To run the test suite:
```shell
mvn clean test
```