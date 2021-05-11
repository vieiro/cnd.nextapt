/*
 * A case reported in the NetBeans dev mailing list.
 */

#define  CMPLX(__real,__imag) \
    _Pragma("clang diagnostic push") \
    _Pragma("clang diagnostic ignored \"-Wcomplex-component-init\"") \
    (double _Complex){(__real),(__imag)} \
    _Pragma("clang diagnostic pop")