#define LOG_TAG "libimsshim"

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include <unistd.h>
#include <sys/types.h>

#include <openssl/ssl.h>

//android::AudioRecord::start(android::AudioSystem::sync_event_t, audio_session_t)
extern int _ZN7android11AudioRecord5startENS_11AudioSystem12sync_event_tE15audio_session_t(
		void *sync_event, int audio_session);

/*
AudioRecord::AudioRecord(
        audio_source_t,
        uint32_t,
        audio_format_t,
        audio_channel_mask_t,
        const String16&,
        size_t,
        callback_t,
        void*,
        uint32_t,
        audio_session_t,
        transfer_type,
        audio_input_flags_t,
        int,
        pid_t,
        const audio_attributes_t*)
*/
extern int _ZN7android11AudioRecordC1E14audio_source_tj14audio_format_tjRKNS_8String16EjPFviPvS6_ES6_j15audio_session_tNS0_13transfer_typeE19audio_input_flags_tiiPK18audio_attributes_t(
	uint32_t, 
	uint32_t, 
	uint32_t, 
	uint32_t, 
	void **, 
	size_t,
	uint32_t, 
	void*, 
	uint32_t, 
	int, 
	uint32_t, 
	uint32_t, 
	int, 
	pid_t, 
	uint32_t);

//Camera::connect(android::Camera *this, int, const android::String16 *, int, int)
extern int _ZN7android6Camera7connectEiRKNS_8String16Eii(void *, int, void **, int, int);

/*
AudioTrack::AudioTrack(
        audio_stream_type_t,
        uint32_t,
        audio_format_t,
        audio_channel_mask_t,
        size_t,
        audio_output_flags_t,
        callback_t,
        void*,
        int32_t,
        audio_session_t,
        transfer_type,
        const audio_offload_info_t *,
        int,
        pid_t,
        const audio_attributes_t*,
        bool,
        float)
*/
extern int _ZN7android10AudioTrackC2E19audio_stream_type_tj14audio_format_tjj20audio_output_flags_tPFviPvS4_ES4_i15audio_session_tNS0_13transfer_typeEPK20audio_offload_info_tiiPK18audio_attributes_tbf(
	void *,
        uint32_t,
        void *,
        void *,
        size_t,
        void *,
        void *,
        void*,
        int32_t,
        void *,
        int32_t,
        const void *,
        int,
        int,
        const void*,
        int,
        float);

//MediaCodec::CreateByType(android::sp<android::ALooper> const&, android::AString const&, bool, int *, int)
int _ZN7android10MediaCodec12CreateByTypeERKNS_2spINS_7ALooperEEERKNS_7AStringEbPii(
		void*, void*, int, int*, pid_t);
/*
AudioRecord::set(
        audio_source_t inputSource,
        uint32_t sampleRate,
        audio_format_t format,
        audio_channel_mask_t channelMask,
        size_t frameCount,
        callback_t cbf,
        void* user,
        uint32_t notificationFrames,
        bool threadCanCallJava,
        audio_session_t sessionId,
        transfer_type transferType,
        audio_input_flags_t flags,
        int uid,
        pid_t pid,
        const audio_attributes_t* pAttributes)
*/
int _ZN7android11AudioRecord3setE14audio_source_tj14audio_format_tjjPFviPvS3_ES3_jb15audio_session_tNS0_13transfer_typeE19audio_input_flags_tiiPK18audio_attributes_t(
	void*,
        uint32_t,
        void*,
        void*,
        size_t,
        void*,
        void*,
        uint32_t,
        int,
        int,
        void*,
        void*,
        int,
        pid_t,
        const void*);

/*
AudioTrack::set(
        audio_stream_type_t streamType,
        uint32_t sampleRate,
        audio_format_t format,
        audio_channel_mask_t channelMask,
        size_t frameCount,
        audio_output_flags_t flags,
        callback_t cbf,
        void* user,
        int32_t notificationFrames,
        const sp<IMemory>& sharedBuffer,
        bool threadCanCallJava,
        audio_session_t sessionId,
        transfer_type transferType,
        const audio_offload_info_t *offloadInfo,
        int uid,
        pid_t pid,
        const audio_attributes_t* pAttributes,
        bool doNotReconnect,
        float maxRequiredSpeed)
*/
int _ZN7android10AudioTrack3setE19audio_stream_type_tj14audio_format_tjj20audio_output_flags_tPFviPvS4_ES4_iRKNS_2spINS_7IMemoryEEEb15audio_session_tNS0_13transfer_typeEPK20audio_offload_info_tiiPK18audio_attributes_tbf(
	void*,
        uint32_t,
        void*,
        void*,
        size_t,
        void*,
        void*,
        void*,
        int32_t,
        const void*,
        int,
        void*,
        void*,
        const void*,
        int,
        pid_t,
        const void*,
        int,
        float);

/******************************************************************/

long SSL_CTX_ctrl(SSL_CTX *ctx, int cmd, long larg, void *parg)
{
	if (cmd == 14) { // SSL_CTRL_EXTRA_CHAIN_CERT
		return SSL_CTX_add_extra_chain_cert(ctx, parg);
	}

	return 0;
}

int _ZN7android10AudioTrack3setE19audio_stream_type_tj14audio_format_tjj20audio_output_flags_tPFviPvS4_ES4_jRKNS_2spINS_7IMemoryEEEbiNS0_13transfer_typeEPK20audio_offload_info_tiiPK18audio_attributes_tb(
	void* streamType,
        uint32_t sampleRate,
        void* format,
        void* channelMask,
        size_t frameCount,
        void* flags,
        void* cbf,
        void* user,
        int32_t notificationFrames,
        const void* sharedBuffer,
        int threadCanCallJava,
        void* sessionId,
        void* transferType,
        const void* offloadInfo,
        int uid,
        pid_t pid,
        const void* pAttributes,
        int doNotReconnect)
{
	return _ZN7android10AudioTrack3setE19audio_stream_type_tj14audio_format_tjj20audio_output_flags_tPFviPvS4_ES4_iRKNS_2spINS_7IMemoryEEEb15audio_session_tNS0_13transfer_typeEPK20audio_offload_info_tiiPK18audio_attributes_tbf(
	streamType,
        sampleRate,
        format,
        channelMask,
        frameCount,
        flags,
        cbf,
        user,
        notificationFrames,
        sharedBuffer,
        threadCanCallJava,
        sessionId,
        transferType,
        offloadInfo,
        uid,
        pid,
        pAttributes,
        doNotReconnect,
	0);
}

int _ZN7android11AudioRecord3setE14audio_source_tj14audio_format_tjjPFviPvS3_ES3_jbiNS0_13transfer_typeE19audio_input_flags_tiiPK18audio_attributes_t(
	void* inputSource,
        uint32_t sampleRate,
        void* format,
        void* channelMask,
        size_t frameCount,
        void* cbf,
        void* user,
        uint32_t notificationFrames,
        int threadCanCallJava,
        int sessionId,
        void* transferType,
        void* flags,
        int uid,
        pid_t pid,
        const void* pAttributes)
{
	return _ZN7android11AudioRecord3setE14audio_source_tj14audio_format_tjjPFviPvS3_ES3_jb15audio_session_tNS0_13transfer_typeE19audio_input_flags_tiiPK18audio_attributes_t(
	inputSource,
	sampleRate,
        format,
        channelMask,
        frameCount,
        cbf,
        user,
        notificationFrames,
        threadCanCallJava,
        sessionId,
        transferType,
        flags,
        uid,
        pid,
        pAttributes);
}

int _ZN7android10MediaCodec12CreateByTypeERKNS_2spINS_7ALooperEEEPKcbPii(
		void *looper, void *mime, int encoder, int *err, pid_t pid)
{
	return _ZN7android10MediaCodec12CreateByTypeERKNS_2spINS_7ALooperEEERKNS_7AStringEbPii(
			looper, mime, encoder, err, pid);
}

int _ZN7android10AudioTrackC2E19audio_stream_type_tj14audio_format_tjj20audio_output_flags_tPFviPvS4_ES4_jiNS0_13transfer_typeEPK20audio_offload_info_tiiPK18audio_attributes_tb(

	void *streamType,
        uint32_t sampleRate,
        void *format,
        void *channelMask,
        size_t frameCount,
        void *flags,
        void *cbf,
        void *user,
        int32_t notificationFrames,
        void *sessionId,
        int32_t transferType,
        const void *offloadInfo,
        int uid,
        pid_t pid,
        const void* pAttributes,
	int doNotReconnect)
{
	return _ZN7android10AudioTrackC2E19audio_stream_type_tj14audio_format_tjj20audio_output_flags_tPFviPvS4_ES4_i15audio_session_tNS0_13transfer_typeEPK20audio_offload_info_tiiPK18audio_attributes_tbf(
	streamType,
	sampleRate,
	format,
	channelMask,
	frameCount,
	flags,
	cbf,
	user,
	notificationFrames,
	sessionId,
	transferType,
	offloadInfo,
	uid,
	pid,
	pAttributes,
	doNotReconnect,
	0);
}

int _ZN7android6Camera7connectEiRKNS_8String16Ei(void *thiz, int cameraId, void **str16P, int clientUid)
{
	return _ZN7android6Camera7connectEiRKNS_8String16Eii(thiz, cameraId, str16P, clientUid, 1000);
}

int _ZN7android11AudioRecord5startENS_11AudioSystem12sync_event_tEi(void* event, int triggerSession)
{
	return _ZN7android11AudioRecord5startENS_11AudioSystem12sync_event_tE15audio_session_t(event, triggerSession);
}

int _ZN7android11AudioRecordC1E14audio_source_tj14audio_format_tjRKNS_8String16EjPFviPvS6_ES6_jiNS0_13transfer_typeE19audio_input_flags_tiiPK18audio_attributes_t(
	uint32_t inputSource, 
	uint32_t sampleRate, 
	uint32_t format, 
	uint32_t channelMask, 
	void **opPackageName, 
	size_t frameCount,
	uint32_t cbf, 
	void* user, 
	uint32_t notificationFrames, 
	int sessionId, 
	uint32_t transferType, 
	uint32_t flags, 
	int uid, 
	pid_t pid, 
	uint32_t pAttributes)
{
	return _ZN7android11AudioRecordC1E14audio_source_tj14audio_format_tjRKNS_8String16EjPFviPvS6_ES6_j15audio_session_tNS0_13transfer_typeE19audio_input_flags_tiiPK18audio_attributes_t(
	inputSource,
	sampleRate,
	format,
	channelMask,
	opPackageName,
	frameCount,
	cbf,
	user,
	notificationFrames,
	sessionId,
	transferType,
	flags,
	uid,
	pid,
	pAttributes);
}

