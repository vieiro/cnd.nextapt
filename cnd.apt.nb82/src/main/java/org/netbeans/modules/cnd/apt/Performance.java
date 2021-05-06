/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.cnd.apt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.BitSet;
import org.netbeans.modules.cnd.antlr.Parser;
import org.netbeans.modules.cnd.antlr.RecognitionException;
import org.netbeans.modules.cnd.antlr.Token;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer;
import org.netbeans.modules.cnd.apt.structure.APTFile;
import org.netbeans.modules.cnd.apt.utils.APTUtils;

/**
 *
 * @author antonio
 */
public class Performance {

    public static void main(String[] args) throws Exception {
        switch(args.length) {
            case 0:
                lexInputStream(System.in);
                break;
            case 1:
                lexFileOrDirectory(new File(args[0]));
                break;
            default:
                System.err.format("Usage: java %s [input-file]%n", Main.class.getName());
                System.err.format("   Lexes stdin if no file is specified.%n");
                System.err.format("   Lexes a file or directory (*.h/*.c) otherwise.%n");
                System.err.format("Use -Dlexer.print=false to disable dumping to stdout.%n");
                System.exit(1);
        }
    }

    private static void lexFileOrDirectory(File fileOrDirectory) throws Exception {
        if (! fileOrDirectory.exists()) {
            System.err.format("File %s does not exist.%n", fileOrDirectory.getAbsolutePath());
            System.exit(2);
        }
        int nfiles = 0;
        double bytes = 0;
        long start = System.currentTimeMillis();
        if (fileOrDirectory.isFile()) {
            nfiles++;
            bytes += fileOrDirectory.length();
            lexFile(fileOrDirectory);
        } else {
            for (File file : fileOrDirectory.listFiles()) {
                String name = file.getName().toLowerCase();
                if (name.endsWith(".h") || name.endsWith(".c")) {
                    lexFile(file);
                    nfiles++;
                    bytes += file.length();
                }
            }
        }
        double seconds = (System.currentTimeMillis() - start) / 1000.0;
        double filesPerSecond = nfiles / seconds;
        double megaBytesPerSecond = bytes / 1024 / seconds;
        System.err.format("Processed %d files in %5.2g seconds (%5.2g files/second %5.2g Mb/second).%n", 
                nfiles, seconds, filesPerSecond, megaBytesPerSecond);
    }

    private static void lexFile(File file) throws Exception {
        System.err.format("-- %s --%n", file.getAbsolutePath());
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), 32*1024)) {
            lexInputStream(input);
        }
    }

    private static void lexInputStream(InputStream input) throws Exception {
        Performance main = new Performance();
        main.dumpTokens(input);
    }

    public void dumpTokens(InputStream input) throws Exception {
        String print = System.getProperty("lexer.print");
        boolean dump = (print != null && print.toLowerCase().equals("false"))? false : true;
        APTLexer lexer = new APTLexer(input);
        lexer.init("input", 0, APTFile.Kind.C_CPP);
        String previousLexerMode = "";
        do {
            Token token = lexer.nextToken();
            if (APTUtils.isEOF(token)) {
                break;
            }
            if (dump) {
                dumpToken(lexer, previousLexerMode, token);
            }
            previousLexerMode = "";
        } while(true);
    }

    private static void dumpToken(APTLexer lexer, String previousLexerMode, Token token) {
        String tokenText = token.getText().replaceAll("(\r\n|\n)", "|");
        String currentLexerMode = "";
        String tokenName = APTUtils.getAPTTokenName(token.getType());
        System.out.format("%4d:%4d:%-20s->%-20s:%-20s:%s%n",
                token.getLine(),
                token.getColumn(),
                previousLexerMode,
                currentLexerMode,
                tokenName,
                tokenText
                );
    }

}