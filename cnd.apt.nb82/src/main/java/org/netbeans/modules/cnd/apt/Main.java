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

import java.io.File;

/**
 * Sports the old org.netbeans.modules.cnd.antlr.Tool compiler to generate a
 * lexer, and then runs the lexer.
 *
 * @author antonio
 */
public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.err.println("Usage: java org.netbeans.modules.cnd.apt.Main file.[ch] ...\n");
            System.exit(1);
        }

        for (String arg : args) {
            File file = new File(arg);
            if (! file.exists()) {
                System.err.format("ERROR: File %s does not exist.%n", file.getAbsolutePath());
            } else {
                APTLexerNoSyntaxErrorTest test = new APTLexerNoSyntaxErrorTest(file.toPath(), true);
                test.test();
            }
        }

    }

}
