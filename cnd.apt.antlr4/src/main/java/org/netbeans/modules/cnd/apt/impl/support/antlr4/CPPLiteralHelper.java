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

import java.util.Map;
import java.util.HashMap;
import org.antlr.v4.runtime.Token;
import org.netbeans.modules.cnd.apt.impl.support.generated.APTLexer;

/**
 * Transformation from identifiers to token types. See the Antlr4 Reference
 * Guide, "Recognizing Languages Whose Keywords Aren’t Fixed"
 */
public final class CPPLiteralHelper {

    public static void checkLiteral(APTLexer lexer) {
        Integer literalType = literals.get(lexer.getText());
        if (literalType != null) {
            lexer.setType(literalType);
        }
    }

    private static Map<String, Integer> literals = new HashMap<String, Integer>() {
        {
            put("alignas", APTLexer.LITERAL_alignas);
            put("_Alignas", APTLexer.LITERAL__Alignas);
            put("__alignof", APTLexer.LITERAL___alignof);
            put("__alignof__", APTLexer.LITERAL___alignof__);
            put("alignof", APTLexer.LITERAL_alignof);
            put("_Alignof", APTLexer.LITERAL__Alignof);
            put("__asm", APTLexer.LITERAL___asm);
            put("__asm__", APTLexer.LITERAL___asm__);
            put("_asm", APTLexer.LITERAL__asm);
            put("asm", APTLexer.LITERAL_asm);
            put("_Atomic", APTLexer.LITERAL__Atomic);
            put("__attribute", APTLexer.LITERAL___attribute);
            put("__attribute__", APTLexer.LITERAL___attribute__);
            put("auto", APTLexer.LITERAL_auto);
            put("bit", APTLexer.LITERAL_bit);
            put("bool", APTLexer.LITERAL_bool);
            put("_Bool", APTLexer.LITERAL__Bool);
            put("break", APTLexer.LITERAL_break);
            put("__builtin_va_list", APTLexer.LITERAL___builtin_va_list);
            put("case", APTLexer.LITERAL_case);
            put("catch", APTLexer.LITERAL_catch);
            put("__cdecl", APTLexer.LITERAL___cdecl);
            put("_cdecl", APTLexer.LITERAL__cdecl);
            put("char16_t", APTLexer.LITERAL_char16_t);
            put("char32_t", APTLexer.LITERAL_char32_t);
            put("char", APTLexer.LITERAL_char);
            put("class", APTLexer.LITERAL_class);
            put("__clrcall", APTLexer.LITERAL___clrcall);
            put("co_await", APTLexer.LITERAL_co_await);
            put("__complex", APTLexer.LITERAL___complex);
            put("__complex__", APTLexer.LITERAL___complex__);
            put("_Complex", APTLexer.LITERAL__Complex);
            put("concept", APTLexer.LITERAL_concept);
            put("const_cast", APTLexer.LITERAL_const_cast);
            put("__const", APTLexer.LITERAL___const);
            put("__const__", APTLexer.LITERAL___const__);
            put("const", APTLexer.LITERAL_const);
            put("constexpr", APTLexer.LITERAL_constexpr);
            put("continue", APTLexer.LITERAL_continue);
            put("co_return", APTLexer.LITERAL_co_return);
            put("co_yield", APTLexer.LITERAL_co_yield);
            put("__declspec", APTLexer.LITERAL___declspec);
            put("_declspec", APTLexer.LITERAL__declspec);
            put("__decltype", APTLexer.LITERAL___decltype);
            put("decltype", APTLexer.LITERAL_decltype);
            put("default", APTLexer.LITERAL_default);
            put("delete", APTLexer.LITERAL_delete);
            put("do", APTLexer.LITERAL_do);
            put("double", APTLexer.LITERAL_double);
            put("dynamic_cast", APTLexer.LITERAL_dynamic_cast);
            put("else", APTLexer.LITERAL_else);
            put("_endasm", APTLexer.LITERAL__endasm);
            put("enum", APTLexer.LITERAL_enum);
            put("explicit", APTLexer.LITERAL_explicit);
            put("export", APTLexer.LITERAL_export);
            put("__extension__", APTLexer.LITERAL___extension__);
            put("extern", APTLexer.LITERAL_extern);
            put("false", APTLexer.LITERAL_false);
            put("__far", APTLexer.LITERAL___far);
            put("_far", APTLexer.LITERAL__far);
            put("final", APTLexer.LITERAL_final);
            put("__finally", APTLexer.LITERAL___finally);
            put("float", APTLexer.LITERAL_float);
            put("__forceinline", APTLexer.LITERAL___forceinline);
            put("for", APTLexer.LITERAL_for);
            put("friend", APTLexer.LITERAL_friend);
            put("__global", APTLexer.LITERAL___global);
            put("goto", APTLexer.LITERAL_goto);
            put("__has_nothrow_assign", APTLexer.LITERAL___has_nothrow_assign);
            put("__has_nothrow_constructor", APTLexer.LITERAL___has_nothrow_constructor);
            put("__has_nothrow_copy", APTLexer.LITERAL___has_nothrow_copy);
            put("__has_trivial_assign", APTLexer.LITERAL___has_trivial_assign);
            put("__has_trivial_constructor", APTLexer.LITERAL___has_trivial_constructor);
            put("__has_trivial_copy", APTLexer.LITERAL___has_trivial_copy);
            put("__has_trivial_destructor", APTLexer.LITERAL___has_trivial_destructor);
            put("__has_virtual_destructor", APTLexer.LITERAL___has_virtual_destructor);
            put("__hidden", APTLexer.LITERAL___hidden);
            put("if", APTLexer.LITERAL_if);
            put("__imag__", APTLexer.LITERAL___imag);
            put("_Imaginary", APTLexer.LITERAL__Imaginary);
            put("__inline", APTLexer.LITERAL___inline);
            put("__inline__", APTLexer.LITERAL___inline__);
            put("_inline", APTLexer.LITERAL__inline);
            put("inline", APTLexer.LITERAL_inline);
            put("__int64", APTLexer.LITERAL___int64);
            put("_int64", APTLexer.LITERAL__int64);
            put("__interrupt", APTLexer.LITERAL___interrupt);
            put("int", APTLexer.LITERAL_int);
            put("__is_abstract", APTLexer.LITERAL___is_abstract);
            put("__is_base_of", APTLexer.LITERAL___is_base_of);
            put("__is_class", APTLexer.LITERAL___is_class);
            put("__is_empty", APTLexer.LITERAL___is_empty);
            put("__is_enum", APTLexer.LITERAL___is_enum);
            put("__is_literal_type", APTLexer.LITERAL___is_literal_type);
            put("__is_pod", APTLexer.LITERAL___is_pod);
            put("__is_polymorphic", APTLexer.LITERAL___is_polymorphic);
            put("__is_standard_layout", APTLexer.LITERAL___is_standard_layout);
            put("__is_trivial", APTLexer.LITERAL___is_trivial);
            put("__is_union", APTLexer.LITERAL___is_union);
            put("long", APTLexer.LITERAL_long);
            put("mutable", APTLexer.LITERAL_mutable);
            put("namespace", APTLexer.LITERAL_namespace);
            put("__near", APTLexer.LITERAL___near);
            put("_near", APTLexer.LITERAL__near);
            put("new", APTLexer.LITERAL_new);
            put("noexcept", APTLexer.LITERAL_noexcept);
            put("_Noreturn", APTLexer.LITERAL__Noreturn);
            put("__null", APTLexer.LITERAL___null);
            put("nullptr", APTLexer.LITERAL_nullptr);
            put("operator", APTLexer.LITERAL_OPERATOR);
            put("override", APTLexer.LITERAL_override);
            put("__pascal", APTLexer.LITERAL___pascal);
            put("_pascal", APTLexer.LITERAL__pascal);
            put("pascal", APTLexer.LITERAL_pascal);
            put("private", APTLexer.LITERAL_private);
            put("protected", APTLexer.LITERAL_protected);
            put("public", APTLexer.LITERAL_public);
            put("__real__", APTLexer.LITERAL___real);
            put("register", APTLexer.LITERAL_register);
            put("reinterpret_cast", APTLexer.LITERAL_reinterpret_cast);
            put("requires", APTLexer.LITERAL_requires);
            put("__restrict", APTLexer.LITERAL___restrict);
            put("__restrict__", APTLexer.LITERAL___restrict__);
            put("restrict", APTLexer.LITERAL_restrict);
            put("return", APTLexer.LITERAL_return);
            put("short", APTLexer.LITERAL_short);
            put("__signed", APTLexer.LITERAL___signed);
            put("__signed__", APTLexer.LITERAL___signed__);
            put("signed", APTLexer.LITERAL_signed);
            put("sizeof", APTLexer.LITERAL_sizeof);
            put("static_assert", APTLexer.LITERAL_static_assert);
            put("_Static_assert", APTLexer.LITERAL__Static_assert);
            put("static_cast", APTLexer.LITERAL_static_cast);
            put("static", APTLexer.LITERAL_static);
            put("__stdcall", APTLexer.LITERAL___stdcall);
            put("_stdcall", APTLexer.LITERAL__stdcall);
            put("struct", APTLexer.LITERAL_struct);
            put("switch", APTLexer.LITERAL_switch);
            put("__symbolic", APTLexer.LITERAL___symbolic);
            put("template", APTLexer.LITERAL_template);
            put("this", APTLexer.LITERAL_this);
            put("thread_local", APTLexer.LITERAL_thread_local);
            put("_Thread_local", APTLexer.LITERAL__Thread_local);
            put("__thread", APTLexer.LITERAL___thread);
            put("throw", APTLexer.LITERAL_throw);
            put("true", APTLexer.LITERAL_true);
            put("__try", APTLexer.LITERAL___try);
            put("try", APTLexer.LITERAL_try);
            put("typedef", APTLexer.LITERAL_typedef);
            put("typename", APTLexer.LITERAL_typename);
            put("__typeof", APTLexer.LITERAL___typeof);
            put("__typeof__", APTLexer.LITERAL___typeof__);
            put("typeof", APTLexer.LITERAL_typeof);
            put("__underlying_type", APTLexer.LITERAL___underlying_type);
            put("union", APTLexer.LITERAL_union);
            put("__unsigned__", APTLexer.LITERAL___unsigned__);
            put("unsigned", APTLexer.LITERAL_unsigned);
            put("using", APTLexer.LITERAL_using);
            put("virtual", APTLexer.LITERAL_virtual);
            put("void", APTLexer.LITERAL_void);
            put("__volatile", APTLexer.LITERAL___volatile);
            put("__volatile__", APTLexer.LITERAL___volatile__);
            put("volatile", APTLexer.LITERAL_volatile);
            put("__w64", APTLexer.LITERAL___w64);
            put("wchar_t", APTLexer.LITERAL_wchar_t);
            put("while", APTLexer.LITERAL_while);
        }
    };

}
