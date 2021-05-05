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
package org.netbeans.modules.cnd.nextapt.antlr4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer;

/**
 *
 */
public class Main implements ANTLRErrorListener{

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
        Main main = new Main();
        main.dumpTokens(input);
    }

    public void dumpTokens(InputStream input) throws Exception {
        String print = System.getProperty("lexer.print");
        boolean dump = (print != null && print.toLowerCase().equals("false"))? false : true;
        APTLexer lexer = new APTLexer(CharStreams.fromStream(input));
        lexer.removeErrorListeners();
        lexer.addErrorListener(this);
        String previousLexerMode = lexer.getModeNames()[lexer._mode];
        do {
            Token token = lexer.nextToken();
            if (token.getType() == Token.EOF) {
                break;
            }
            if (dump) {
                dumpToken(lexer, previousLexerMode, token);
            }
            previousLexerMode = lexer.getModeNames()[lexer._mode];
        } while(true);
    }

    private static void dumpToken(APTLexer lexer, String previousLexerMode, Token token) {
        String tokenText = token.getText().replaceAll("(\r\n|\n)", "|");
        String currentLexerMode = lexer.getModeNames()[lexer._mode];
        String tokenName = lexer.VOCABULARY.getSymbolicName(token.getType());
        System.out.format("%4d:%4d:%-20s->%-20s:%-20s:%s%n",
                token.getLine(),
                token.getCharPositionInLine(),
                previousLexerMode,
                currentLexerMode,
                tokenName,
                tokenText
                );
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int line, int column, String message, RecognitionException re) {
        System.err.format("Syntax error on %d:%d %s%n",
                line,
                column,
                message
                );
        System.exit(3);
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
