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
import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.BitSet;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Performs a test of APTLexer
 */
public abstract class APTLexerNoSyntaxErrorTest implements ANTLRErrorListener {

    protected final Path inputFilePath;
    protected final Document document;
    protected final Element tokensElement;

    protected APTLexerNoSyntaxErrorTest(Path inputFilePath) throws Exception {
        this.inputFilePath = inputFilePath;
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        f.setNamespaceAware(false);
        document= f.newDocumentBuilder().newDocument();
        tokensElement = document.createElement("tokens");
        document.appendChild(tokensElement);
    }

    protected void addToken(Lexer lexer, String previousLexerMode, Token token) throws Exception {
        Element tokenElement = document.createElement("token");
        tokenElement.setAttribute("previousMode", previousLexerMode);
        tokenElement.setAttribute("mode", lexer.getModeNames()[lexer._mode]);
        tokenElement.setAttribute("type", "" + token.getType());
        tokenElement.setAttribute("name", lexer.getVocabulary().getSymbolicName(token.getType()));
        tokenElement.setAttribute("line", "" + token.getLine());
        tokenElement.setAttribute("col", "" + token.getCharPositionInLine());
        tokenElement.setAttribute("offset", "" + token.getStartIndex());
        tokenElement.appendChild(document.createCDATASection(token.getText()));
        tokensElement.appendChild(tokenElement);
    }

    public final void test() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath.toFile()))) {
            APTLexer lexer = new APTLexer(CharStreams.fromReader(reader));
            lexer.addErrorListener(this);
            doTest(lexer);
        }
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(document), new StreamResult(out));
        System.out.format("--%s--%n", getClass().getName());
        System.out.println(out.toString());
    }

    private void doTest(APTLexer lexer) throws Exception {
        String previousLexerMode = lexer.getModeNames()[lexer._mode];
        do {
            Token token = lexer.nextToken();
            if (token.getType() == Token.EOF) {
                break;
            }
            addToken(lexer, previousLexerMode, token);
            previousLexerMode = lexer.getModeNames()[lexer._mode];
        } while(true);
        System.out.println("Done.");
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int i, int i1, String string, RecognitionException re) {
        System.err.format("Syntax error on file: %s: %s string=%s%n",
                inputFilePath.toString(),
                re.getMessage(),
                string
                );
        throw new IllegalStateException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {
        throw new IllegalStateException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
        throw new IllegalStateException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
        throw new IllegalStateException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
