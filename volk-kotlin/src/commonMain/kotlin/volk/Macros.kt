@file:Suppress("FunctionName", "MagicNumber", "NOTHING_TO_INLINE")

package volk

inline fun VK_MAKE_API_VERSION(variant: UInt, major: UInt, minor: UInt, patch: UInt): UInt =
    (variant shl 29) or (major shl 22) or (minor shl 12) or patch

inline fun VK_MAKE_VERSION(major: UInt, minor: UInt, patch: UInt): UInt =
    ((major shl 22) or (minor shl 12) or patch)

inline fun VK_VERSION_MAJOR(version: UInt): UInt =
    version.shr(22)

inline fun VK_VERSION_MINOR(version: UInt): UInt =
    version.shr(12).and(0x3ffu)

inline fun VK_VERSION_PATCH(version: UInt): UInt =
    version.and(0xfffu)
