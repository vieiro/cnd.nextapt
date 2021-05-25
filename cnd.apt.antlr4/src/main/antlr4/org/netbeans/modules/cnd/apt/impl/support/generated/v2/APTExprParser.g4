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

/*
 * APTExprParser is  an  Antlr-4  version of  the  old cnd.apt
 * "aptexpr.g" parser grammar written in Antlr 2.
 * This is a parser for preprocessor expressions.
 *
 * References:
 * - Antlr4 grammar structure: https://github.com/antlr/antlr4/blob/master/doc/grammars.md
 * - Antlr4 parser rulres: https://github.com/antlr/antlr4/blob/master/doc/parser-rules.md
 * - Antlr4 interpreters: https://github.com/antlr/antlr4/blob/master/doc/interpreters.md
 * - Antlr4 left recursive rules: https://github.com/antlr/antlr4/blob/master/doc/left-recursion.md
 * - Anlr3 to Antlr4 migration: https://tomassetti.me/migrating-from-antlr2-to-antlr4/
 */

parser grammar APTExprParser;

options {
    // TODO: Move this language setting to the command line, so the grammar is
    // language agnostic.
	language = Java;
	tokenVocab = APTLexer;
}

@header {
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

 /* This file was automatically generated from APTExprParser.g4. */

}

expr:
    defined # definedOp
    | constant # constantOp
    | LPAREN expr RPAREN  # parenthesisOp
    | (PLUS | MINUS | TILDE | NOT) expr # unaryOp
    | expr (STAR | DIVIDE | MOD) expr # binaryArithmeticOp
    | expr (PLUS | MINUS) expr # binaryArithmeticOp
    | expr (SHIFTLEFT | SHIFTRIGHT) expr # binaryBitOp
    | expr (LESSTHAN | LESSTHANOREQUALTO | GREATERTHAN | GREATERTHANOREQUALTO) expr # binaryLogicalOp
    | expr (EQUAL | NOTEQUAL) expr # binaryLogicalOp
    | expr AMPERSAND expr # binaryBitOp
    | expr BITWISEXOR expr # binaryBitOp
    | expr BITWISEOR expr # binaryBitOp
    | expr AND expr # binaryLogicalOp
    | expr OR expr # binaryLogicalOp
    | expr QUESTIONMARK expr COLON expr # ternaryOp;


defined:
    DEFINED
    (
        DEFINE_NAME_REF
        | LPAREN DEFINE_NAME_REF RPAREN
    );

constant:
    LITERAL_true # constantTrueOp
    | LITERAL_false # constantFalseOp
    | DEFINE_NAME_REF # constantNameOp
    | FLOATONE # constantRealOp
    | DECIMALINT # constantIntegerOp
    | LONG # constantLongOp
    | ULONG # constantUlongOp
    | CHAR_LITERAL # constantCharOp
    | BINARYINT # constantBinaryOp
    | HEXADECIMALINT # constantHexadecimalOp
    | OCTALINT # constantOctalIntOp
    ;
