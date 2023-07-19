#!/bin/bash

NAME="todo"
SRCDIR="./srcs"
OBJDIR="./objs"
MAIN="GUI"
JDBC="./jdbc/sqlite-jdbc-3.42.0.0.jar"

SRCS=$(find "$SRCDIR" -mindepth 1 -type f -name "*.java")

# コンパイル
javac -d "$OBJDIR" $SRCS

# 実行
java -cp "$OBJDIR"';'"$JDBC" "$MAIN"