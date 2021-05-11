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
package org.netbeans.modules.cnd.apt.impl.support.antlr4;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTExprParser;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTExprParserBaseListener;

/**
 * An implementation of APTExprParserBaserListener that evaluates 
 * C preprocessor expressions.
 */
public class APTExprParserEvaluator extends APTExprParserBaseListener {


    public APTExprParserEvaluator() {
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        String errorMessage = node.toString();
        throw new RuntimeException("Error: visitErrorNode: " + errorMessage);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String errorMessage = node.toString();
        throw new RuntimeException("Error: visitErrorNode: " + errorMessage);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantBinaryOp(APTExprParser.ConstantBinaryOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantCharOp(APTExprParser.ConstantCharOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantUlongOp(APTExprParser.ConstantUlongOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantLongOp(APTExprParser.ConstantLongOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantHexadecimalOp(APTExprParser.ConstantHexadecimalOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantIntegerOp(APTExprParser.ConstantIntegerOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantRealOp(APTExprParser.ConstantRealOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantNameOp(APTExprParser.ConstantNameOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantFalseOp(APTExprParser.ConstantFalseOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantTrueOp(APTExprParser.ConstantTrueOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitDefined(APTExprParser.DefinedContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitTernaryOp(APTExprParser.TernaryOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitParenthesisOp(APTExprParser.ParenthesisOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitBinaryLogicalOp(APTExprParser.BinaryLogicalOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitUnaryOp(APTExprParser.UnaryOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitConstantOp(APTExprParser.ConstantOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitBinaryArithmeticOp(APTExprParser.BinaryArithmeticOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitBinaryBitOp(APTExprParser.BinaryBitOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }

    @Override
    public void exitDefinedOp(APTExprParser.DefinedOpContext ctx) {
        System.out.println("CONTEXT: " + ctx);
    }
    
}
