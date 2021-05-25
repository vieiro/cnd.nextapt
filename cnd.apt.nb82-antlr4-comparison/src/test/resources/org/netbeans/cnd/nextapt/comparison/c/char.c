// Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements; and to You under the Apache License, Version 2.0.
#include <stdio.h>

/*
 * A C styled comment.
 */

int main(int argc, const char * argv) 
{
    const char constants [] = {
        u'ñ',
        L'ñ',
        U'ñ',
        '\a',
        '\n',
        '\t',
        '\r',
        '\f',
        '\
',
        '#',
        'a',
        '"'
    };

    int a = 10;
    int b = 0123;
    int c = 0b101011;
    long l = 123l;
    double d = .123;
    double d = .123e3;
    double d2 = 0.123;
    double d3 = 0.123e+10;
    double d4 = 0.123e-10;


    return 0;
}

