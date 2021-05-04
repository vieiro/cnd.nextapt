#/usr/bin/env sh

for A in `find tests -name "*.c"`
do
    java -jar ../cnd.apt.nb82/target/cnd.apt.nb82-1.0-SNAPSHOT-jar-with-dependencies.jar $A
done
