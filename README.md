# design-dropbox
Implementing Dropbox, referencing design from [Hello Interview](https://www.hellointerview.com/learn/system-design/problem-breakdowns/dropbox)

## Commands
```bash
gradle dependencies
gradle build
gradle bootRun

# watch
gradle wrapper

./gradlew bootRun --continuous
# in the other terminal
./gradlew build --continuous

# create a jar file
gradle bootJar
# list the contents of the jar file
jar tvf build/libs/bitly-0.0.1-SNAPSHOT.jar
# run the jar file
java -jar build/libs/bitly-0.0.1-SNAPSHOT.jar

# testing
./gradlew test
./gradlew test --info
./gradlew test --debug
./gradlew clean test
./gradlew test --tests "com.bitly.url.UrlServiceTest"
```

# Deep Dives
