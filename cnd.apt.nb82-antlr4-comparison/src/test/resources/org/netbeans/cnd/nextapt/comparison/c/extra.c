/*
 * Some tokens that are not supported in the NB 8.2 Lexer.
 */

// Some weird #include alternatives
%:%: include "another.c"
// Objective-C Strings
const NSString * STRING1 = @"This is a simple string with a #include";

