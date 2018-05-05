#include <sys/types.h>
#include <stdint.h>

// Android O exports
extern "C" uint32_t _ZN6google8protobuf2io16CodedInputStream15ReadTagFallbackEj(void* thiz, uint32_t first_byte_or_zero);
extern "C" bool _ZN6google8protobuf8internal14WireFormatLite9ReadBytesEPNS0_2io16CodedInputStreamEPNSt3__112basic_stringIcNS6_11char_traitsIcEENS6_9allocatorIcEEEE(void* thiz, void* input, void* value);
extern "C" uint8_t* _ZN6google8protobuf2io17CodedOutputStream20WriteVarint64ToArrayEyPh(void* thiz, uint64_t value, uint8_t* target);
extern "C" void _ZN6google8protobuf8internal20RepeatedPtrFieldBase12SwapFallbackINS0_16RepeatedPtrFieldINS0_7MessageEE11TypeHandlerEEEvPS2_(void* thiz, void* other);
extern "C" void* _ZN6google8protobuf8internal26GeneratedMessageReflectionC2EPKNS0_10DescriptorEPKNS0_7MessageEPKiiiiPKNS0_14DescriptorPoolEPNS0_14MessageFactoryEiii(void* thiz, void* descriptor, void* default_instance, uint32_t offsets[], int32_t has_bits_offset, int32_t unknown_fields_offset, int32_t extensions_offset, void* pool, void* factory, uint32_t object_size, uint32_t arena_offset, uint32_t is_default_instance_offset = -1);
extern "C" int64_t _ZN6google8protobuf2io16CodedInputStream20ReadVarint32FallbackEj(void* thiz, uint32_t first_byte_or_zero);


extern "C" uint32_t _ZN6google8protobuf2io16CodedInputStream15ReadTagFallbackEv(void* thiz)
{
    return _ZN6google8protobuf2io16CodedInputStream15ReadTagFallbackEj(thiz, 0);
}

extern "C" bool _ZN6google8protobuf8internal14WireFormatLite10ReadStringEPNS0_2io16CodedInputStreamEPNSt3__112basic_stringIcNS6_11char_traitsIcEENS6_9allocatorIcEEEE(void* thiz, void* input, void* value)
{
    return _ZN6google8protobuf8internal14WireFormatLite9ReadBytesEPNS0_2io16CodedInputStreamEPNSt3__112basic_stringIcNS6_11char_traitsIcEENS6_9allocatorIcEEEE(thiz, input, value);
}

extern "C" uint8_t* _ZN6google8protobuf2io17CodedOutputStream28WriteVarint32FallbackToArrayEjPh(void* thiz, uint32_t value, uint8_t* target)
{
    return _ZN6google8protobuf2io17CodedOutputStream20WriteVarint64ToArrayEyPh(thiz, value, target);
}

extern "C" void _ZN6google8protobuf8internal20RepeatedPtrFieldBase4SwapEPS2_(void* thiz, void* other)
{
    _ZN6google8protobuf8internal20RepeatedPtrFieldBase12SwapFallbackINS0_16RepeatedPtrFieldINS0_7MessageEE11TypeHandlerEEEvPS2_(thiz, other);
}

extern "C" void* _ZN6google8protobuf8internal26GeneratedMessageReflectionC1EPKNS0_10DescriptorEPKNS0_7MessageEPKiiiiPKNS0_14DescriptorPoolEPNS0_14MessageFactoryEi(void* thiz, void* descriptor, void* default_instance, uint32_t offsets[], int32_t has_bits_offset, int32_t unknown_fields_offset, int32_t extensions_offset, void* pool, void* factory, uint32_t object_size)
{
    return _ZN6google8protobuf8internal26GeneratedMessageReflectionC2EPKNS0_10DescriptorEPKNS0_7MessageEPKiiiiPKNS0_14DescriptorPoolEPNS0_14MessageFactoryEiii(thiz,  descriptor, default_instance, offsets, has_bits_offset, unknown_fields_offset, extensions_offset, pool, factory, object_size, 0, -1);
}

extern "C" bool _ZN6google8protobuf2io16CodedInputStream20ReadVarint32FallbackEPj(void* thiz, uint32_t* value)
{
    *value = (uint32_t)_ZN6google8protobuf2io16CodedInputStream20ReadVarint32FallbackEj(thiz, 0);
    return true;
}

extern "C" void _ZN6google8protobuf8internal13VerifyVersionEiiPKc(void* thiz, int32_t headerVersion, int32_t minLibraryVersion, const char* filename) {}

