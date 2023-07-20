# Java_Swing_ToDo

Java Swing, sqlite3を用いたToDo管理アプリ

グループ課題

## Usage
```
make
```

or

```
bash execute.sh
```

or

```
javac -d ./obj ./srcs/* && java -cp ”./objs;./jdbc/sqlite-jdbc-3.42.0.0.jar” GUI
```

**OSに応じてMakefile, execute.sh内の「：」または「;」を変更してください**

linux or mac  「:」

windows 「;」

### databaseの初期化
```
cd database
sqlite3 database.db < insert.sql
```

## Version
- openjdk 20 2023-03-21
  - OpenJDK Runtime Environment (build 20+36-2344)
  - OpenJDK 64-Bit Server VM (build 20+36-2344, mixed mode, sharing)

- sqlite3 3.40.1
- sqlite-jdbc 3.42.0.0
  - [Releases](https://github.com/xerial/sqlite-jdbc/releases)
