// Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements; and to You under the Apache License, Version 2.0.
#define PI 3.1416
#define PI2 PI \
    + 1 - 1
#define _LINUX_DEFINED

#define SUM(N) (N)+1+2

#define MUL (A, B) \
        (N)+1+2

#define EOF    (-1)

#define A 3

#define VA(...) printf(...);

#if A \
    == 3
// A is 3
#else
// A is not 3
#endif

#define EMPTY_MACRO() "This is an empty macro!"

#error "This is an error"

#pragma "This is a pragma"

#include <stdio.h>

int main(int argc, const char * argv) 
{
    printf("PI2 is %d\n", PI2, SUM(PI2));
    return 0;
}

