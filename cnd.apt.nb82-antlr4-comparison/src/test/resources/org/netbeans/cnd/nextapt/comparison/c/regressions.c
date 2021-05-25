/*
 * A case reported in the NetBeans dev mailing list.
 */

#define  CMPLX(__real,__imag) \
    _Pragma("clang diagnostic push") \
    _Pragma("clang diagnostic ignored \"-Wcomplex-component-init\"") \
    (double _Complex){(__real),(__imag)} \
    _Pragma("clang diagnostic pop")

/*
 * A case in Microsoft's ehdata_values.h
 * C99 6.4.4.4p10: "The value of an integer character constant containing more 
 * than one character (e.g., 'ab'), or containing a character or escape 
 * sequence that does not map to a single-byte execution character, 
 * is implementation-defined."
 */
#define EH_EXCEPTION_NUMBER ('msc' | 0xE0000000) // The NT Exception # that we use


/*
 * A case in Microsoft's include\immintrin.h
 */
#define _mm256_loadu2_m128(/* float const* */ hiaddr, \
                           /* float const* */ loaddr) \
    _mm256_set_m128(_mm_loadu_ps(hiaddr), _mm_loadu_ps(loaddr))

/*
 * A case in Microsoft's include\xatomic_wait.h
 */
_INLINE_VAR constexpr unsigned long _Atomic_wait_no_timeout       = 0xFFFF'FFFF; // Pass as partial timeout

/*
 * A case in clang's __clang_cuda_math.h, where a #ifdef has an identifier
 * followed by an expression.
 */
// Specialized version of __DEVICE__ for functions with void return type. Needed
// because the OpenMP overlay requires constexpr functions here but prior to
// c++14 void return functions could not be constexpr.
#pragma push_macro("__DEVICE_VOID__")
#ifdef __OPENMP_NVPTX__ && defined(__cplusplus) && __cplusplus < 201402L
#define __DEVICE_VOID__ static __attribute__((always_inline, nothrow))
#else
#define __DEVICE_VOID__ __DEVICE__
#endif
