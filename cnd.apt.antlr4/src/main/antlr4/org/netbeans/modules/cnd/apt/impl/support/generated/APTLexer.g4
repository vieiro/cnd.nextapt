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
 * APTLexer is  an  Antlr-4  version of  the  old cnd.apt  
 * "aptlexer.g" written in Antlr 2.
 *
 * References:
 * - Antlr4 Lexer Rules https://github.com/antlr/antlr4/blob/master/doc/lexer-rules.md
 * - Antlr4 Predicates in Lexer Rules: https://github.com/antlr/antlr4/blob/master/doc/predicates.md#predicates-in-lexer-rules
 */

lexer grammar APTLexer;

// Some channels used to send tokens we're not interested in
channels {COMMENT}

options {
    language = Java;
}

tokens {
END_PREPROC_DIRECTIVE,
LITERAL_alignas, LITERAL__Alignas, LITERAL___alignof,
LITERAL___alignof__, LITERAL_alignof, LITERAL__Alignof,
LITERAL___asm, LITERAL___asm__, LITERAL__asm, LITERAL_asm,
LITERAL__Atomic, LITERAL___attribute, LITERAL___attribute__,
LITERAL_auto, LITERAL_bit, LITERAL_bool, LITERAL__Bool,
LITERAL_break, LITERAL___builtin_va_list, LITERAL_case,
LITERAL_catch, LITERAL___cdecl, LITERAL__cdecl, LITERAL_char16_t,
LITERAL_char32_t, LITERAL_char, LITERAL_class, LITERAL___clrcall,
LITERAL_co_await, LITERAL___complex, LITERAL___complex__,
LITERAL__Complex, LITERAL_concept, LITERAL_const_cast, LITERAL___const,
LITERAL___const__, LITERAL_const, LITERAL_constexpr, LITERAL_continue,
LITERAL_co_return, LITERAL_co_yield, LITERAL___declspec,
LITERAL__declspec, LITERAL___decltype, LITERAL_decltype,
LITERAL_default, LITERAL_delete, LITERAL_do, LITERAL_double,
LITERAL_dynamic_cast, LITERAL_else, LITERAL__endasm, LITERAL_enum,
LITERAL_explicit, LITERAL_export, LITERAL___extension__,
LITERAL_extern, LITERAL_false, LITERAL___far, LITERAL__far,
LITERAL_final, LITERAL___finally, LITERAL_float, LITERAL___forceinline,
LITERAL_for, LITERAL_friend, LITERAL___global, LITERAL_goto,
LITERAL___has_nothrow_assign, LITERAL___has_nothrow_constructor,
LITERAL___has_nothrow_copy, LITERAL___has_trivial_assign,
LITERAL___has_trivial_constructor, LITERAL___has_trivial_copy,
LITERAL___has_trivial_destructor, LITERAL___has_virtual_destructor,
LITERAL___hidden, LITERAL_if, LITERAL___imag, LITERAL__Imaginary,
LITERAL___inline, LITERAL___inline__, LITERAL__inline,
LITERAL_inline, LITERAL___int64, LITERAL__int64, LITERAL___interrupt,
LITERAL_int, LITERAL___is_abstract, LITERAL___is_base_of,
LITERAL___is_class, LITERAL___is_empty, LITERAL___is_enum,
LITERAL___is_literal_type, LITERAL___is_pod, LITERAL___is_polymorphic,
LITERAL___is_standard_layout, LITERAL___is_trivial, LITERAL___is_union,
LITERAL_long, LITERAL_mutable, LITERAL_namespace, LITERAL___near,
LITERAL__near, LITERAL_new, LITERAL_noexcept, LITERAL__Noreturn,
LITERAL___null, LITERAL_nullptr, LITERAL_OPERATOR, LITERAL_override,
LITERAL___pascal, LITERAL__pascal, LITERAL_pascal, LITERAL_private,
LITERAL_protected, LITERAL_public, LITERAL___real, LITERAL_register,
LITERAL_reinterpret_cast, LITERAL_requires, LITERAL___restrict,
LITERAL___restrict__, LITERAL_restrict, LITERAL_return, LITERAL_short,
LITERAL___signed, LITERAL___signed__, LITERAL_signed, LITERAL_sizeof,
LITERAL_static_assert, LITERAL__Static_assert, LITERAL_static_cast,
LITERAL_static, LITERAL___stdcall, LITERAL__stdcall, LITERAL_struct,
LITERAL_switch, LITERAL___symbolic, LITERAL_template,
LITERAL_this, LITERAL_thread_local, LITERAL__Thread_local,
LITERAL___thread, LITERAL_throw, LITERAL_true, LITERAL___try,
LITERAL_try, LITERAL_typedef, LITERAL_typename, LITERAL___typeof,
LITERAL___typeof__, LITERAL_typeof, LITERAL___underlying_type,
LITERAL_union, LITERAL___unsigned__, LITERAL_unsigned, LITERAL_using,
LITERAL_virtual, LITERAL_void, LITERAL___volatile, LITERAL___volatile__,
LITERAL_volatile, LITERAL___w64, LITERAL_wchar_t, LITERAL_while
}


@lexer::header {
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

/* This file was automatically generated by Antlr4 from APTLexer.g4. */

import org.netbeans.modules.cnd.apt.impl.support.antlr4.CPPLiteralHelper;

}

@lexer::members {
    public static enum LanguageMode {
        C_CPP,
        FORTRAN
    };

    private LanguageMode languageMode = LanguageMode.C_CPP;

    /*
     * A flag that indicates that we're reading a preprocessor expression in DEFAULT_MODE.
     * This means that:
     * - A '\' followed that \n or \r\n must be ignored.
     * - A \n or a \r\n must emit END_PREPROC_DIRECTIVE token.
     */
    protected boolean isPreprocExpressionSubmode = false;

    protected void enterPreprocExpressionSubmode() {
        isPreprocExpressionSubmode = true;
        mode(DEFAULT_MODE);
    }

    protected void leavePreprocExpressionSubmode() {
        isPreprocExpressionSubmode = false;
        mode(DEFAULT_MODE);
    }
}

// ----------------------------------------------------------------------
// DEFAULT_MODE
// This is the mode that the lexer runs by default.
// - In this mode we recognize:
//   - C/C++/Objective-C strings
//   - C/C++ block comments and comments (in C/C++ mode).
//   - Fortran comments (in Fortran mode).
//   - Some punctuation tokens (';', '/', '/=' ,etc.)
//   - C preprocessor directives (using antlr4 'lexical modes').
//   - We do _not_ recognize language specific operators/keywords.
// - Additional posterior lexers/filters are responsible for language
//   specific keyword/operator recognition.
// ----------------------------------------------------------------------

/*
FORTRAN_COMMENT:
    { languageMode == LanguageMode.FORTRAN }?
    '!' ~[\r\n]* -> channel(COMMENT);

FORTRAN_77_COMMENT:
    { languageMode == LanguageMode.FORTRAN && getCharPositionInLine() == 0}?
    [cC*] ~[\r\n]* -> channel(COMMENT);
*/

LINE_COMMENT:
    '//' ~[\r\n]* -> channel(COMMENT);

BLOCK_COMMENT:
    '/*' .*? '*/' -> channel(COMMENT);

fragment STRING_ENCODING:
    'u8' | 'u' | 'U' | 'L' | '@';

// See https://docs.microsoft.com/en-us/cpp/c-language/octal-and-hexadecimal-character-specifications?view=msvc-160
fragment OCTAL_DIGIT:
    (
        [0-7]
        | [0-7][0-7]
        | [0-7][0-7][0-7]
    );

fragment HEXADECIMAL_DIGIT:
    (
        [0-9A-Fa-f]
        | [0-9A-Fa-f] [0-9A-Fa-f]
        | [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f]
    );

fragment UNICODE_POINT:
    [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f];

fragment UNICODE_POINT_UTF16:
    [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f] [0-9A-Fa-f];

// Characters allowed in a non-raw string
// https://docs.microsoft.com/en-us/cpp/c-language/c-character-constants?view=msvc-160
fragment STRING_CHAR:
    (
        '\\' ('\r\n' | '\n') // Continuation line
        | '\\"'
        | '\\x' HEXADECIMAL_DIGIT
        | '\\u' UNICODE_POINT
        | '\\U' UNICODE_POINT_UTF16
        | '\\' ['!?abfnrtv\\] // Character escape sequences (\r \a)
        | '\\' OCTAL_DIGIT
        | ~[\n\r"\\]
    );

        
STRING_LITERAL:
    STRING_ENCODING?
    (
        '"' STRING_CHAR* '"'
    );

// Characters

fragment CHAR_ENCODING:
    'u' | 'U' | 'L';

fragment CHAR_CONTENT:
    (
        '\\' ('\r\n' | '\n') // Continuation line
        | '\\x' HEXADECIMAL_DIGIT
        | '\\' ['!?abfnrtv\\] // Character escape sequences (\r \a)
        | '\\' OCTAL_DIGIT
        | '\\u' UNICODE_POINT
        | '\\U' UNICODE_POINT_UTF16
        | ~[\n\r\\]
    );

CHAR_LITERAL:
    CHAR_ENCODING?  '\'' CHAR_CONTENT '\'' ;

// Numbers

fragment DECIMAL:
    [0-9] [0-9]*;

ULONG:
    DECIMAL ('ull' | 'uLL' | 'Ull' | 'ULL' | 'llu' | 'LLu' | 'LLU')?;

LONG:
    DECIMAL ('ul' | 'uL' | 'Ul' | 'UL' | 'lu' | 'lU' | 'Lu' | 'LU')?;

INTEGER:
    DECIMAL;

HEXADECIMAL:
    '0' [xX] [0-9a-fA-F]+;

BINARY:
    '0b' [0-1]+;

fragment SIGN:
    [+-];

REAL:
    DECIMAL '.' DECIMAL? ([eE] SIGN? DECIMAL)?;

// #include "... and %:+ include "...
INCLUDE:
    ('#' | '%:'+)
    [ \t]*
    'include'
    [ \t]*
    -> mode(READ_INCLUDE_MODE);

// #include_next "... and %:+ include_next "...
INCLUDE_NEXT:
    ('#' | '%:'+)
    [ \t]*
    'include_next'
    [ \t]*
    -> mode(READ_INCLUDE_MODE);

// #define "... and %:+ include "...
DEFINE:
    ('#' | '%:'+)
    [ \t]*
    'define'
    [ \t]* -> mode(READ_DEFINE_MODE);

IFDEF:
    ('#' | '%:'+)
    [ \t]*
    'ifdef'
    [ \t]* -> mode(READ_DEFINE_REF_MODE);

IFNDEF:
    ('#' | '%:'+)
    [ \t]*
    'ifndef'
    [ \t]* -> mode(READ_DEFINE_REF_MODE);

PRAGMA:
    ('#' | '%:'+)
    [ \t]*
    'pragma'
    { enterPreprocExpressionSubmode(); }
    ;

ERROR:
    ('#' | '%:'+)
    [ \t]*
    'error'
    { enterPreprocExpressionSubmode(); }
    ;

UNDEF:
    ('#' | '%:'+)
    [ \t]*
    'undef'
    [ \t]* -> mode(READ_DEFINE_REF_MODE);

IF:
    ('#' | '%:'+)
    [ \t]*
    'if'
    [ \t]*
    { enterPreprocExpressionSubmode(); }
    ;

DEFINED: 'defined';

ELIF:
    ('#' | '%:'+)
    [ \t]*
    'elif'
    [ \t]*
    { enterPreprocExpressionSubmode(); }
    ;

ELSE:
    ('#' | '%:'+)
    [ \t]*
    'else'
    [ \t]*;

ENDIF:
    ('#' | '%:'+)
    [ \t]*
    'endif'
    [ \t]*;

// TODO: Trigraphs?


HASH: '#';

// Digraphs
// https://en.wikipedia.org/wiki/Digraphs_and_trigraphs#C
DIGRAPH_LSQUARE         : '<:'  -> type(LSQUARE);
DIGRAPH_RSQUARE         : ':>'  -> type(RSQUARE);
DIGRAPH_LCURLY          : '<%'  -> type(LCURLY);
DIGRAPH_RCURLY          : '%>'  -> type(RCURLY);
DIGRAPH_HASH            : '%:'+ -> type(HASH);

// Operators and punctuation
ELLIPSIS                : '...';
DOT                     : '.';
COMMA                   : ',';
QUESTIONMARK            : '?';
SEMICOLON               : ';';
LPAREN                  : '(';
RPAREN                  : ')';
LSQUARE                 : '[';
RSQUARE                 : ']';
LCURLY                  : '{';
RCURLY                  : '}';
TILDE                   : '~';
EQUAL                   : '==';
ASSIGNEQUAL             : '=';
DIVIDEEQUAL             : '/=';
DIVIDE                  : '/';
TIMESEQUAL              : '*=';
STAR                    : '*';
MODEQUAL                : '%=';
MOD                     : '%';
NOTEQUAL                : '!=';
NOT                     : '!';
BITWISEANDEQUAL         : '&=';
AND                     : '&&';
AMPERSAND               : '&';
OR                      : '||';
BITWISEOREQUAL          : '|=';
BITWISEOR               : '|';
BITWISEXOREQUAL         : '^=';
BITWISEXOR              : '^';
SCOPE                   : '::';
COLON                   : ':';
SHIFTLEFT               : '<<';
LESSTHANOREQUALTO       : '<=';
LESSTHAN                : '<'; // TODO: Review this one <:: C++11 2.5p3 bullet 2???
AT                      : '@';
GRAVE_ACCENT            : '`';
SHIFTRIGHTEQUAL         : '>>=';
SHIFTRIGHT              : '>>';
GREATERTHANOREQUALTO    : '>=';
GREATERTHAN             : '>';
POINTERTOMBR            : '->*';
POINTERTO               : '->';
MINUSMINUS              : '--';
MINUSEQUAL              : '-=';
MINUS                   : '-';
PLUSPLUS                : '++';
PLUSEQUAL               : '+=';
PLUS                    : '+';

// Identifiers
// https://docs.microsoft.com/en-us/cpp/c-language/c-identifiers?view=msvc-160

IDENTIFIER:
    [a-zA-Z_] [a-zA-Z0-9_$]*
    { CPPLiteralHelper.checkLiteral(this); };

SPACES:
    [ \t\f]+ -> skip;

PREPROC_CONTINUATION:
    ('\\\n' | '\\\r\n') -> skip;

EOL:
    [\r\n]+
    {
        if (isPreprocExpressionSubmode) {
            setText("");
            setType(END_PREPROC_DIRECTIVE);
            leavePreprocExpressionSubmode();
        } else {
            skip();
        }
    };

// ----------------------------------------------------------------------
// READ_INCLUDE_MODE
// We enter this mode after '#include "'
// (or equivalent)
// ----------------------------------------------------------------------

mode READ_INCLUDE_MODE;

INCLUDE_CONTINUATION:
    ('\\\n' | '\\\r\n') -> skip;

INCLUDE_LOCAL:
    '"' ~[\r\n]+? '"';

INCLUDE_SYSTEM:
    '<' ~[\r\n]+? '>';

INCLUDE_LINE_COMMENT:
    '//' ~[\r\n]* -> channel(COMMENT);

INCLUDE_BLOCK_COMMENT:
    '/*' .*? '*/' -> channel(COMMENT);

INCLUDE_ID:
    [a-zA-Z_$] [a-zA-Z0-9_$]*;

END_INCLUDE_DIRECTIVE:
    ('\r\n' | '\n') -> mode(DEFAULT_MODE);

INCLUDE_WHITESPACE:
    [ \t] -> skip;

// ----------------------------------------------------------------------
// READ_DEFINE_MODE
// We enter this mode after '#define '
// (or equivalent)
// ----------------------------------------------------------------------
mode READ_DEFINE_MODE;

DEFINE_IDENTIFIER:
    [a-zA-Z_$] [a-zA-Z0-9_$]*
    {
        if (_input.LA(1) == '(') {
            mode(READ_MACRO_ARGS_MODE);
        } else {
            enterPreprocExpressionSubmode();
        }
    };

// ----------------------------------------------------------------------
// READ_DEFINE_REF_MODE
// We enter this mode after '#define '
// (or equivalent)
// ----------------------------------------------------------------------
mode READ_DEFINE_REF_MODE;

DEFINE_NAME_REF:
    [a-zA-Z_$] [a-zA-Z0-9_$]*;

DEFINE_REF_LINE_COMMENT:
    '//' ~[\r\n]* -> channel(COMMENT);

DEFINE_REF_BLOCK_COMMENT:
    '/*' .*? '*/' -> channel(COMMENT);

DEFINE_REF_WHITESPACE:
    [ \t] -> skip;

END_REF_DIRECTIVE:
    ('\r\n' | '\n') -> mode(DEFAULT_MODE);

// ----------------------------------------------------------------------
// READ_MACRO_ARGS_MODE
// We enter this mode after '#define XXXX'
// ----------------------------------------------------------------------
mode READ_MACRO_ARGS_MODE;


MACRO_WHITESPACE:
    [ \t] -> skip;

MACRO_SEPARATOR:
    ',' -> skip;

MACRO_CONTINUATION:
    ('\\\n' | '\\\r\n') -> skip;

MACRO_ARGS_START: '(';

END_MACRO_DIRECTIVE:
    ')'
    { enterPreprocExpressionSubmode(); }
    ;
ARGUMENT_ELLIPSIS:
    '...';
ARGUMENT_IDENTIFIER:
    [a-zA-Z_$] [a-zA-Z0-9_$]*;

