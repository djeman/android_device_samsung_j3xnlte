
LOCAL_PATH := device/samsung/j3xnlte

$(call inherit-product, $(SRC_TARGET_DIR)/product/languages_full.mk)

# The gps config appropriate for this device
$(call inherit-product, device/common/gps/gps_eu_supl.mk)

# Get non-open-source specific aspects
$(call inherit-product-if-exists, vendor/samsung/j3xnlte/j3xnlte-vendor.mk)

# Overlays
PRODUCT_PACKAGE_OVERLAYS += $(LOCAL_PATH)/overlay

# Sprd proprietaries drm libomx
$(call inherit-product, vendor/sprd/proprietaries/proprietaries-scx35l.mk)

# init services
$(call inherit-product, $(LOCAL_PATH)/init/init_rc.mk)

# gps
$(call inherit-product, $(LOCAL_PATH)/gps/device-gps.mk)

# treble
$(call inherit-product, $(LOCAL_PATH)/treble.mk)

# Permissions
PRODUCT_COPY_FILES += \
    frameworks/native/data/etc/handheld_core_hardware.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/handheld_core_hardware.xml \
    frameworks/native/data/etc/android.hardware.camera.autofocus.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.camera.autofocus.xml \
    frameworks/native/data/etc/android.hardware.camera.flash-autofocus.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.camera.flash-autofocus.xml \
    frameworks/native/data/etc/android.hardware.camera.front.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.camera.front.xml \
    frameworks/native/data/etc/android.hardware.wifi.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.wifi.xml \
    frameworks/native/data/etc/android.hardware.wifi.direct.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.wifi.direct.xml \
    frameworks/native/data/etc/android.hardware.sensor.proximity.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.sensor.proximity.xml \
    frameworks/native/data/etc/android.hardware.sensor.accelerometer.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.sensor.accelerometer.xml \
    frameworks/native/data/etc/android.hardware.faketouch.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.faketouch.xml \
    frameworks/native/data/etc/android.hardware.touchscreen.multitouch.jazzhand.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.touchscreen.multitouch.jazzhand.xml \
    frameworks/native/data/etc/android.hardware.touchscreen.multitouch.distinct.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.touchscreen.multitouch.distinct.xml \
    frameworks/native/data/etc/android.hardware.touchscreen.multitouch.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.touchscreen.multitouch.xml \
    frameworks/native/data/etc/android.hardware.touchscreen.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.touchscreen.xml \
    frameworks/native/data/etc/android.hardware.usb.accessory.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.usb.accessory.xml \
    frameworks/native/data/etc/android.hardware.usb.host.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.usb.host.xml \
    frameworks/native/data/etc/android.software.sip.voip.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.software.sip.voip.xml \
    frameworks/native/data/etc/android.hardware.telephony.gsm.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.telephony.gsm.xml \
    frameworks/native/data/etc/com.android.nfc_extras.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/com.android.nfc_extras.xml \
    frameworks/native/data/etc/android.hardware.nfc.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.xml \
    frameworks/native/data/etc/android.hardware.nfc.hce.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.nfc.hce.xml \
    frameworks/native/data/etc/com.nxp.mifare.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/com.nxp.mifare.xml \
    frameworks/native/data/etc/android.hardware.location.gps.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.location.gps.xml \
    frameworks/native/data/etc/android.hardware.bluetooth.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.bluetooth.xml \
    frameworks/native/data/etc/android.hardware.bluetooth_le.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.hardware.bluetooth_le.xml \
    frameworks/native/data/etc/android.software.midi.xml:$(TARGET_COPY_OUT_VENDOR)/etc/permissions/android.software.midi.xml

# Audio configuration
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/configs/audio/audio_hw.xml:$(TARGET_COPY_OUT_VENDOR)/etc/audio_hw.xml \
    $(LOCAL_PATH)/configs/audio/audio_para:$(TARGET_COPY_OUT_VENDOR)/etc/audio_para \
    $(LOCAL_PATH)/configs/audio/audio_policy.conf:$(TARGET_COPY_OUT_VENDOR)/etc/audio_policy.conf \
    $(LOCAL_PATH)/configs/audio/codec_pga.xml:$(TARGET_COPY_OUT_VENDOR)/etc/codec_pga.xml \
    $(LOCAL_PATH)/configs/audio/tiny_hw.xml:$(TARGET_COPY_OUT_VENDOR)/etc/tiny_hw.xml

# BT configuration
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/configs/bluetooth/iop_bt.db:$(TARGET_COPY_OUT_VENDOR)/etc/bluetooth/iop_bt.db \
    $(LOCAL_PATH)/configs/bluetooth/iop_device_list.conf:$(TARGET_COPY_OUT_VENDOR)/etc/bluetooth/iop_device_list.conf

# Media Profile
PRODUCT_COPY_FILES += \
    frameworks/av/media/libstagefright/data/media_codecs_google_audio.xml:$(TARGET_COPY_OUT_VENDOR)/etc/media_codecs_google_audio.xml \
    frameworks/av/media/libstagefright/data/media_codecs_google_telephony.xml:$(TARGET_COPY_OUT_VENDOR)/etc/media_codecs_google_telephony.xml \
    frameworks/av/media/libstagefright/data/media_codecs_google_video_le.xml:$(TARGET_COPY_OUT_VENDOR)/etc/media_codecs_google_video_le.xml \
    frameworks/av/media/libstagefright/data/media_codecs_google_video.xml:$(TARGET_COPY_OUT_VENDOR)/etc/media_codecs_google_video.xml \
    $(LOCAL_PATH)/configs/media/media_codecs.xml:$(TARGET_COPY_OUT_VENDOR)/etc/media_codecs.xml \
    $(LOCAL_PATH)/configs/media/media_profiles.xml:$(TARGET_COPY_OUT_VENDOR)/etc/media_profiles.xml

# Keylayouts
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/keylayout/gpio-keys.kl:$(TARGET_COPY_OUT_VENDOR)/usr/keylayout/gpio-keys.kl \
    $(LOCAL_PATH)/keylayout/headset-keyboard.kl:$(TARGET_COPY_OUT_VENDOR)/usr/keylayout/headset-keyboard.kl \
    $(LOCAL_PATH)/keylayout/sci-keypad.kl:$(TARGET_COPY_OUT_VENDOR)/usr/keylayout/sci-keypad.kl \
    $(LOCAL_PATH)/keylayout/sec_touchkey.kl:$(TARGET_COPY_OUT_VENDOR)/usr/keylayout/sec_touchkey.kl \
    $(LOCAL_PATH)/keylayout/sprd-eic-keys.kl:$(TARGET_COPY_OUT_VENDOR)/usr/keylayout/sprd-eic-keys.kl \
    $(LOCAL_PATH)/keylayout/sprd-gpio-keys.kl:$(TARGET_COPY_OUT_VENDOR)/usr/keylayout/sprd-gpio-keys.kl \
    $(LOCAL_PATH)/keylayout/Synaptics_RMI4_TouchPad_Sensor.idc:$(TARGET_COPY_OUT_VENDOR)/usr/idc/Synaptics_RMI4_TouchPad_Sensor.idc \
    $(LOCAL_PATH)/keylayout/Synaptics_HID_TouchPad.idc:$(TARGET_COPY_OUT_VENDOR)/usr/idc/Synaptics_HID_TouchPad.idc

# Device uses high-density artwork where available
PRODUCT_AAPT_CONFIG := normal hdpi xhdpi
PRODUCT_AAPT_PREF_CONFIG := xhdpi

# Set default USB interface
PRODUCT_DEFAULT_PROPERTY_OVERRIDES += \
persist.sys.usb.config=mtp,adb

# TinyAlsa utils
PRODUCT_PACKAGES += \
    tinyplay \
    tinycap \
    tinymix \
    tinypcminfo

PRODUCT_PACKAGES += \
    libjackshm \
    libjackserver \
    libjack \
    libjacklogger \
    androidshmservice \
    jackd \
    jack_dummy \
    jack_alsa \
    jack_opensles \
    jack_loopback \
    in \
    out \
    jack_connect \
    jack_disconnect \
    jack_lsp \
    jack_showtime \
    jack_simple_client \
    jack_transport \
    libasound \
    libglib-2.0 \
    libgthread-2.0 \
    libfluidsynth

# Misc. libs
PRODUCT_PACKAGES += \
    libstlport \
    libboringssl-compat

# MDNIE
PRODUCT_PACKAGES += \
    AdvancedDisplay

# Samsung
PRODUCT_PACKAGES += \
    SamsungServiceMode \
    Torch

# Filesystem
PRODUCT_PACKAGES += \
    fsck.f2fs

# Live Wallpapers
PRODUCT_PACKAGES += \
    LiveWallpapers \
    LiveWallpapersPicker \
    VisualizationWallpapers \
    librs_jni

# Misc
PRODUCT_PACKAGES += \
    libxml2 \
    Stk

# SeLinux
PRODUCT_PACKAGES += \
    file_contexts \
    property_contexts \
    seapp_contexts \
    service_contexts \
    selinux_version

# NFC
PRODUCT_PACKAGES += \
    com.android.nfc_extras \
    nfc_nci.nqx.default \
    nqnfcee_access.xml \
    nqnfcse_access.xml \
    Tag
#    com.nxp.nfc.nq \
#    NQNfcNci \

PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/configs/nfc/libnfc-brcm.conf:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-brcm.conf \
    $(LOCAL_PATH)/configs/nfc/libnfc-nxp.conf:$(TARGET_COPY_OUT_VENDOR)/etc/libnfc-nxp.conf \
    $(LOCAL_PATH)/configs/nfc/nfcee_access.xml:$(TARGET_COPY_OUT_VENDOR)/etc/nfcee_access.xml

# WPA supplicant
PRODUCT_PACKAGES += \
    hostapd \
    libwpa_client \
    wpa_supplicant

PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/configs/wpa_supplicant/mcs.ini:$(TARGET_COPY_OUT_VENDOR)/etc/wifi/mcs.ini \
    $(LOCAL_PATH)/configs/wpa_supplicant/wpa_supplicant.conf:$(TARGET_COPY_OUT_VENDOR)/etc/wifi/wpa_supplicant.conf \
    $(LOCAL_PATH)/configs/wpa_supplicant/wpa_supplicant_overlay.conf:$(TARGET_COPY_OUT_VENDOR)/etc/wifi/wpa_supplicant_overlay.conf \
    $(LOCAL_PATH)/configs/wpa_supplicant/p2p_supplicant_overlay.conf:$(TARGET_COPY_OUT_VENDOR)/etc/wifi/p2p_supplicant_overlay.conf

# Usb accessory
PRODUCT_PACKAGES += \
    com.android.future.usb.accessory

# Misc other modules
PRODUCT_PACKAGES += \
    audio.a2dp.default \
    audio.usb.default \
    audio.r_submix.default

# Ramdisk
PRODUCT_PACKAGES += \
    fstab.sc8830 \
    init.board.rc \
    init.recovery.board.rc \
    init.cali.rc \
    init.j3xnlte.rc \
    init.j3xnlte_base.rc \
    init.sc8830.rc \
    init.sc8830.usb.rc \
    init.sc8830_ss.rc \
    init.wifi.rc \
    ueventd.sc8830.rc \
    sswap \
    bgcompact \
    ffu

# e2fsprog
PRODUCT_PACKAGES += \
    e2fsck \
    blkid \
    resize2fs

# strongSwan
PRODUCT_PACKAGES += \
    charon \
    libcharon \
    libhydra \
    libstrongswan

PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/configs/strongswan/strongswan.conf:$(TARGET_COPY_OUT_VENDOR)/etc/strongswan.conf

# libexifa, libjpega, libexif
PRODUCT_PACKAGES += \
    libexif \
    libexifa \
    libjpega 

# KeyUtils
PRODUCT_PACKAGES += \
    libkeyutils
    
#libdmverity_hashgen
PRODUCT_PACKAGES += \
    libdmverity_hashgen  
 
# dm_verity_hash
PRODUCT_PACKAGES += \
    dm_verity_hash

PRODUCT_PACKAGES += \
    charger_res_images

# Camera
PRODUCT_PACKAGES += \
    Snap

PRODUCT_PACKAGES += \
    libgpsshim \
    libprotobufshim

PRODUCT_PACKAGES += \
    camera.sc8830 \
    power.sc8830 \
    libcamoem \
    lights.sc8830 \
    hwcomposer.sc8830 \
    sprd_gsp.sc8830 \
    libGLES_android \
    gralloc.sc8830 \
    libion \
    libmemoryheapion \
    libefuse \
    libomxil-bellagio \
    libstagefright_sprd_h264dec \
    libstagefright_sprd_h264enc \
    libstagefright_sprd_mp3dec \
    libstagefright_sprd_mpeg4dec \
    libstagefright_sprd_mpeg4enc \
    libstagefright_sprd_soft_h264dec \
    libstagefright_sprd_soft_mpeg4dec \
    libstagefright_sprd_vpxdec \
    libstagefright_soft_imaadpcmdec \
    libstagefright_soft_mjpgdec \
    libdumpdata \
    libnvexchange \
    libvbpga \
    libvbeffect \
    libatchannel \
    audio.primary.sc8830 \
    libstagefrighthw_cm \
    libstagefrighthw \
    libomxvpu \
    liboemcrypto \
    modem_control \
    cp_diskserver \
    phasecheckserver \
    radvd \
    refnotify \
    download \
    wcnd \
    wcnd_cli \
    slog \
    slogctl \
    tcp \
    slog.conf \
    slog.conf.user \
    slogmodem \
    flush_slog_modem \
    cplogctl \
    sprd_res_monitor \
    sprd_monitor-userdebug.conf \
    sprd_monitor-user.conf \
    monitor.conf \
    capture_oprofile.sh \
    liboprofiledaemon \
    hcidump \
    libbt-vendor \
    librilutils \
    libbm \
    libGLES_mali.so \
    libsecnativefeature \
    libsecril-client \
    libreference-ril \
    rild \
    libril \
    engpc

# RIL
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/ril/vendor/libsec-ril.so:$(TARGET_COPY_OUT_VENDOR)/lib/libsec-ril.so \
    $(LOCAL_PATH)/ril/vendor/libsec-ril-dsds.so:$(TARGET_COPY_OUT_VENDOR)/lib/libsec-ril-dsds.so

PRODUCT_PROPERTY_OVERRIDES += \
    ro.telephony.ril_class=SlteRIL

# Storage
PRODUCT_PROPERTY_OVERRIDES += \
    ro.sdcardfs.enable=true

# Wifi
PRODUCT_PROPERTY_OVERRIDES += \
    wifi.interface=wlan0

# Default props
PRODUCT_DEFAULT_PROPERTY_OVERRIDES += \
    telephony.lteOnCdmaDevice=0 \
    ro.telephony.default_cdma_sub=1 \
    persist.radio.apm_sim_not_pwdn=1 \
    persist.radio.add_power_save=1 \
    rild.libpath=/vendor/lib/libsec-ril.so \
    rild.libpath2=/vendor/lib/libsec-ril-dsds.so \
    persist.security.ams.enforcing=1

# We have enough storage space to hold precise GC data
PRODUCT_TAGS += dalvik.gc.type-precise

# Dalvik heap config
$(call inherit-product, frameworks/native/build/phone-xhdpi-1024-dalvik-heap.mk)

