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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.junit.jupiter.api.Test;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer;

/**
 * Verifies that the lexer does not produce any syntax error.
 */
public class NoSyntaxErrorTest {

    public NoSyntaxErrorTest() {
    }

    public static class SyntaxErrorDetector implements ANTLRErrorListener {

        @Override
        public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int i, int i1, String string, RecognitionException re) {
            throw new UnsupportedOperationException("Test failed");
        }

        @Override
        public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {
            throw new UnsupportedOperationException("Test failed");
        }

        @Override
        public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
            throw new UnsupportedOperationException("Test failed");
        }

        @Override
        public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
            throw new UnsupportedOperationException("Test failed");
        }

    }

    @Test
    public void testShouldNotGenerateAnySyntaxError() throws Exception {
        String[] testFiles = {
            "c/char.c",
            "c/define.c",
            "c/include.c",
            "c/strings.c",};

        for (String testFile : testFiles) {
            System.out.format("TEST NO SYNTAX ERRORS: %s%n", testFile);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(NoSyntaxErrorTest.class.getResourceAsStream(testFile), StandardCharsets.UTF_8))) {
                APTLexer lexer = new APTLexer(CharStreams.fromReader(reader));
                lexer.addErrorListener(new SyntaxErrorDetector());
                String previousLexerMode = lexer.getModeNames()[lexer._mode];
                do {
                    Token token = lexer.nextToken();
                    if (token.getType() == Token.EOF) {
                        break;
                    }
                    dumpToken(lexer, previousLexerMode, token);
                    previousLexerMode = lexer.getModeNames()[lexer._mode];
                } while (true);
            }
        }
    }

    private static void dumpToken(APTLexer lexer, String previousLexerMode, Token token) {
        String tokenText = token.getText().replaceAll("\n", "\\n").replaceAll("\r", "\\r");
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

}
