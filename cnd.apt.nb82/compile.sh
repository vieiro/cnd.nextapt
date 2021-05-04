#!/usr/bin/env sh
java -cp nb82/org/netbeans/nb82/cnd.apt/8.2/cnd.apt-8.2.jar org.netbeans.modules.cnd.antlr.Tool \
    -o src/main/java/org/netbeans/modules/cnd/apt/impl/support/generated -debug aptlexer.g
