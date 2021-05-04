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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import org.netbeans.modules.cnd.antlr.Parser;
import org.netbeans.modules.cnd.antlr.RecognitionException;
import org.netbeans.modules.cnd.antlr.Token;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer;
import org.netbeans.modules.cnd.apt.structure.APTFile;
import org.netbeans.modules.cnd.apt.utils.APTUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Performs a test of APTLexer
 */
public class APTLexerNoSyntaxErrorTest {

    protected final Path inputFilePath;
    protected final Document document;
    protected final Element tokensElement;
    protected final boolean generateResult;

    protected APTLexerNoSyntaxErrorTest(Path inputFilePath, boolean generateResult) throws Exception {
        this.inputFilePath = inputFilePath;
        this.generateResult = generateResult;
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        f.setNamespaceAware(false);
        document = f.newDocumentBuilder().newDocument();
        tokensElement = document.createElement("tokens");
        document.appendChild(tokensElement);
    }

    protected void addToken(APTLexer lexer, String previousLexerMode, Token token) throws Exception {
        Element tokenElement = document.createElement("token");
        tokenElement.setAttribute("previousMode", previousLexerMode);
        tokenElement.setAttribute("mode", "DEFAULT_MODE");
        tokenElement.setAttribute("type", "" + token.getType());
        tokenElement.setAttribute("name", APTUtils.getAPTTokenName(token.getType()));
        tokenElement.setAttribute("line", "" + token.getLine());
        tokenElement.setAttribute("col", "" + token.getColumn());
        tokenElement.setAttribute("offset", "" + 0);
        tokenElement.appendChild(document.createCDATASection(token.getText()));
        tokensElement.appendChild(tokenElement);
    }

    public final void test() throws Exception {
        long start = System.currentTimeMillis();
        System.out.format("Test %s", inputFilePath.toString());
        File inputFile = inputFilePath.toFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            APTLexer lexer = new APTLexer(reader);
            lexer.init(inputFile.getName(), 0, APTFile.Kind.C_CPP);
            doTest(lexer);
        }
        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format(" OK (%5.3g seconds)%n", time);
        dumpResultFile();
    }

    private void dumpResultFile() throws Exception {
        if (generateResult) {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer out = new StringWriter();
            tf.transform(new DOMSource(document), new StreamResult(out));
            File inputFile = inputFilePath.toFile();
            File outputFile = new File(inputFile.getParentFile(), inputFile.getName() + "-nb82.xml");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(out.toString());
            }
        }
    }

    private void doTest(APTLexer lexer) throws Exception {
        do {
            Token token = lexer.nextToken();
            if (APTUtils.isEOF(token)) {
                break;
            }
            addToken(lexer, "DEFAULT_MODE", token);
        } while (true);
    }

}
