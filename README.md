# design-dropbox
Implementing Dropbox, referencing design from [Hello Interview](https://www.hellointerview.com/learn/system-design/problem-breakdowns/dropbox)

## Commands
```bash
tree -I ".gradle|.vscode|bin|build|gradle|infrastructure"
gradle dependencies

./gradlew bootRun --continuous
# in the other terminal
./gradlew build --continuous

# testing
./gradlew test
./gradlew test --info
./gradlew test --debug
./gradlew clean test
./gradlew test --tests "com.bitly.url.UrlServiceTest"
```

# Deep Dives
