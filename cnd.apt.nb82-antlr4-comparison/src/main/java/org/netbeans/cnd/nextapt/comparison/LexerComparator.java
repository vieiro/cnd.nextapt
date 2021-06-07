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
package org.netbeans.cnd.nextapt.comparison;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.netbeans.modules.cnd.apt.impl.support.generated.v2.APTLexer;
import org.netbeans.modules.cnd.apt.structure.APTFile;
import org.netbeans.modules.cnd.apt.utils.APTUtils;

/**
 * Compares the NB82 and ANTLR4 cnd.apt Lexers.
 */
public class LexerComparator implements ANTLRErrorListener {

    public static void main(String[] args) throws Exception {
        switch (args.length) {
            case 1:
                lexFileOrDirectory(new File(args[0]));
                break;
            default:
                System.err.format("Usage: java %s [input-file]%n", LexerComparator.class.getName());
                System.err.format("   Lexes stdin if no file is specified.%n");
                System.err.format("   Lexes a file or directory (*.h,*.hpp,*.c, *.cpp) otherwise.%n");
                System.err.format("Use -Dlexer.print=false to disable dumping to stdout.%n");
                System.exit(1);
        }
    }

    private static void lexFileOrDirectory(File fileOrDirectory) throws Exception {
        if (!fileOrDirectory.exists()) {
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
                if (name.endsWith(".h") || name.endsWith(".c") || name.endsWith(".hpp") || name.endsWith(".cpp")) {
                    lexFile(file);
                    nfiles++;
                    bytes += file.length();
                }
            }
        }
        double seconds = (System.currentTimeMillis() - start) / 1000.0;
        double filesPerSecond = nfiles == 0 ? 0 : nfiles / seconds;
        double megaBytesPerSecond = bytes == 0 ? 0 : bytes / 1024 / seconds;
        System.err.format("Processed %d files in %5.2g seconds (%5.2g files/second %5.2g Mb/second).%n",
                nfiles, seconds, filesPerSecond, megaBytesPerSecond);
    }

    private static void lexFile(File file) throws Exception {
        System.err.format("-- %s --%n", file.getAbsolutePath());
        try (
                BufferedInputStream inputA = new BufferedInputStream(new FileInputStream(file), 32 * 1024);
                BufferedInputStream inputB = new BufferedInputStream(new FileInputStream(file), 32 * 1024)) {
            lexInputStream(inputA, inputB);
        }
    }

    static void lexInputStream(InputStream inputA, InputStream inputB) throws Exception {
        LexerComparator main = new LexerComparator();
        main.dumpTokens(inputA, inputB);
    }

    public void dumpTokens(InputStream inputA, InputStream inputB) throws Exception {
        String print = System.getProperty("lexer.print");
        boolean dump = (print != null && print.toLowerCase().equals("false")) ? false : true;

        // New tokens
        APTLexer lexer = new APTLexer(CharStreams.fromStream(inputA));
        lexer.removeErrorListeners();
        lexer.addErrorListener(this);
        String previousLexerMode = lexer.getModeNames()[lexer._mode];
        ArrayList<Token> newTokens = new ArrayList<>();
        ArrayList<String> newTokenNames = new ArrayList<>();

        do {
            Token newToken = lexer.nextToken();
            if (newToken.getType() == Token.EOF) {
                break;
            }
            String newTokenName = lexer.VOCABULARY.getSymbolicName(newToken.getType());
            newTokens.add(newToken);
            newTokenNames.add(newTokenName);
        } while (true);

        org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer oldLexer = new org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer(inputB);
        oldLexer.init("INPUT", 0, APTFile.Kind.C_CPP);
        ArrayList<org.netbeans.modules.cnd.antlr.Token> oldTokens = new ArrayList();

        do {
            org.netbeans.modules.cnd.antlr.Token oldToken = oldLexer.nextToken();
            if (APTUtils.isEOF(oldToken)) {
                break;
            }
            oldTokens.add(oldToken);
        } while (true);

        for (int i = 0, j = 0; i < newTokens.size() && j < oldTokens.size(); i++, j++) {
            Token newToken = newTokens.get(i);
            String newTokenName = newTokenNames.get(i);
            org.netbeans.modules.cnd.antlr.Token oldToken = oldTokens.get(j);
            String oldTokenName = APTUtils.getAPTTokenName(oldToken.getType());

            if (! newTokenName.equals(oldTokenName)) {
                System.out.format("%4d:%4d NEW %s OLD %s %4d:%4d%n",
                        newToken.getLine(),
                        newToken.getCharPositionInLine(),
                        newTokenName,
                        oldTokenName,
                        oldTokens.get(j).getLine(),
                        oldTokens.get(j).getColumn()
                        );

                // Move new token to next line
                int newTokenLine = newToken.getLine();
                while(i < newTokens.size() && newTokens.get(i).getLine() == newTokenLine ) {
                    i++;
                }

                // Move old token to next line
                int oldTokenLine = oldToken.getLine();
                while(j < oldTokens.size() && oldTokens.get(j).getLine() == oldTokenLine) {
                    j++;
                }

                /*
                boolean advanceNext = false;
                boolean advanceOld = false;
                if (i + 1 < newTokens.size()) {
                    String nextNewTokenName = newTokenNames.get(i+1);
                    if (nextNewTokenName.equals(oldTokenName)) {
                        advanceNext = true;
                    }
                }
                if (! advanceNext && j + 1 < oldTokens.size()) {
                    String nextOldTokenName = APTUtils.getAPTTokenName(oldTokens.get(j+1).getType());
                    if (newTokenName.equals(nextOldTokenName)) {
                        advanceOld = true;
                    }
                }
                if (advanceNext) {
                    i++;
                }
                if (advanceOld) {
                    j++;
                }
                */
            }
        }
    }

    private void dumpTokens(APTLexer lexer, Token newToken, org.netbeans.modules.cnd.antlr.Token oldToken) {
        String newTokenName = lexer.VOCABULARY.getSymbolicName(newToken.getType());
        String oldTokenName = APTUtils.getAPTTokenName(oldToken.getType());
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int line, int column, String message, RecognitionException re) {
        System.err.format("Syntax error on %d:%d %s%n",
                line,
                column,
                message
        );
        System.err.println("Please report this error on the dev mailing list, including the line where the error happeened.%n");
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
