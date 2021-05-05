// Including local files
// This is a comment with a hash # include
/* This is a block comment with a hash # include */

# include "hello.c"

%:%: include "another.c"

# include <stdio.h>

const char * STRING1 = "This is a simple string with a #include";
const char * STRING1 = u"This is a simple string with a #include";
const char * STRING1 = u8"This is a simple string with a #include";
const char * STRING1 = L"This is a simple string with a #include";
const NSString * STRING1 = @"This is a simple string with a #include";

const char * STRING_CONTINUATED = "This is a simple\
    string\
    with \
    #include \
    with continuations";


