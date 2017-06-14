
TARGET_OTA_ASSERT_DEVICE := j3xnlte,j3xnltexx

# inherit from the proprietary version
-include vendor/samsung/j3xnlte/BoardConfigVendor.mk

COMMON_GLOBAL_CFLAGS += -DSPRD_HARDWARE

TARGET_ARCH := arm
TARGET_NO_BOOTLOADER := true
TARGET_NO_RADIOIMAGE := true
TARGET_BOARD_PLATFORM := sc8830
TARGET_CPU_ABI := armeabi-v7a
TARGET_CPU_ABI2 := armeabi
TARGET_ARCH_VARIANT := armv7-a-neon
TARGET_CPU_VARIANT := cortex-a7
TARGET_CPU_SMP := true
ARCH_ARM_HAVE_TLS_REGISTER := true

BOARD_NEEDS_MEMORYHEAPION_SPRD := true
BOARD_USE_EMMC := true

DEVICE_RESOLUTION := 720x1280

TARGET_BOOTLOADER_BOARD_NAME := SC9830I

BOARD_VENDOR := samsung

# Include path
TARGET_SPECIFIC_HEADER_PATH := device/samsung/j3xnlte/include

#TARGET_PREBUILT_KERNEL := device/samsung/j3xnlte/kernel
#TARGET_PREBUILT_DTB := device/samsung/j3xnlte/dt.img

TARGET_KERNEL_SOURCE := kernel/samsung/j3xnlte
#TARGET_KERNEL_CONFIG := j3xnlte_defconfig
TARGET_KERNEL_CONFIG := j3xnlte_permissive_defconfig
NEED_KERNEL_MODULE_ROOT := true

SPRD_MODULES:
	make -C vendor/sprd/modules/libgpu/gpu/utgard/ MALI_PLATFORM=sc8830 BUILD=debug KDIR=$(KERNEL_OUT) clean
	make -C vendor/sprd/modules/libgpu/gpu/utgard/ MALI_PLATFORM=sc8830 BUILD=debug KDIR=$(KERNEL_OUT)
	mv vendor/sprd/modules/libgpu/gpu/utgard/mali.ko $(KERNEL_MODULES_OUT)
	make -C vendor/sprd/wcn/wifi/sc2331/6.0/ SPRDWL_PLATFORM=sc8830 USING_PP_CORE=2 BUILD=debug KDIR=$(KERNEL_OUT) clean
	make -C vendor/sprd/wcn/wifi/sc2331/6.0/ SPRDWL_PLATFORM=sc8830 USING_PP_CORE=2 BUILD=debug KDIR=$(KERNEL_OUT)
	mv vendor/sprd/wcn/wifi/sc2331/6.0/sprdwl.ko $(KERNEL_MODULES_OUT)
	mkdir -p $(PRODUCT_OUT)/system/lib/modules
	ln -sf /lib/modules/autotst.ko $(PRODUCT_OUT)/system/lib/modules/autotst.ko
	ln -sf /lib/modules/mali.ko $(PRODUCT_OUT)/system/lib/modules/mali.ko
	ln -sf /lib/modules/mmc_test.ko $(PRODUCT_OUT)/system/lib/modules/mmc_test.ko
	ln -sf /lib/modules/sprdwl.ko $(PRODUCT_OUT)/system/lib/modules/sprdwl.ko

TARGET_KERNEL_MODULES := SPRD_MODULES
TARGET_IGNORE_RO_BOOT_REVISION := true

BOARD_KERNEL_CMDLINE := console=ttyS1,115200n8
BOARD_KERNEL_BASE := 0x00000000
BOARD_KERNEL_PAGESIZE := 2048
BOARD_MKBOOTIMG_ARGS := --kernel_offset 0x00008000 --ramdisk_offset 0x01000000 --tags_offset 0x00000100 --cmdline 'console=ttyS1,115200n8'

BOARD_CUSTOM_BOOTIMG_MK := device/samsung/j3xnlte/bootimg.mk
BOARD_KERNEL_SEPARATED_DT := true

TARGET_CUSTOM_DTBTOOL := dtbToolSprd

# fix this up by examining /proc/mtd on a running device
BOARD_BOOTIMAGE_PARTITION_SIZE := 20971520
BOARD_RECOVERYIMAGE_PARTITION_SIZE := 20971520
BOARD_SYSTEMIMAGE_PARTITION_SIZE := 2147483648
BOARD_USERDATAIMAGE_PARTITION_SIZE := 5272240128
BOARD_CACHEIMAGE_PARTITION_SIZE := 209715200
BOARD_PERSISTIMAGE_PARTITION_SIZE := 10485760
BOARD_FLASH_BLOCK_SIZE := 131072

BOARD_CACHEIMAGE_FILE_SYSTEM_TYPE := ext4
BOARD_HAS_LARGE_FILESYSTEM := true
TARGET_USERIMAGES_USE_EXT4 := true
BOARD_SUPPRESS_SECURE_ERASE := true

# CMHW
BOARD_HARDWARE_CLASS := hardware/samsung/cmhw/

# Init
TARGET_PROVIDES_INIT_RC := true
TARGET_INIT_VENDOR_LIB := libinit_j3lte
TARGET_RECOVERY_DEVICE_MODULES := libinit_j3lte
TARGET_UNIFIED_DEVICE := true

# Boot animation
TARGET_SCREEN_HEIGHT := 1280
TARGET_SCREEN_WIDTH := 720
TARGET_BOOTANIMATION_PRELOAD := true
TARGET_BOOTANIMATION_TEXTURE_CACHE := true

# telephony
BOARD_PROVIDES_LIBRIL := true
BOARD_MODEM_TYPE := tss310
USE_BOOT_AT_DIAG := true
BOARD_RIL_CLASS := ../../../device/samsung/j3xnlte/ril/

# healthd
BOARD_HAL_STATIC_LIBRARIES := libhealthd.j3xnlte

# lights
TARGET_PROVIDES_LIBLIGHT := true

# Graphics
TARGET_BOARD_PLATFORM_GPU := utgard
TARGET_GPU_PP_CORE := 2
USE_SPRD_HWCOMPOSER  := true
USE_OPENGL_RENDERER := true
USE_OVERLAY_COMPOSER_GPU := true
NUM_FRAMEBUFFER_SURFACE_BUFFERS := 3

USE_RGB_VIDEO_LAYER  := true

# Storage
TARGET_USE_CUSTOM_LUN_FILE_PATH := "/sys/devices/20200000.usb/gadget/lun%d/file"
BOARD_VOLD_DISC_HAS_MULTIPLE_MAJORS := true
BOARD_VOLD_MAX_PARTITIONS := 65

# Enable HW based full disk encryption
#TARGET_HW_DISK_ENCRYPTION := true

# Sensors
TARGET_NO_SENSOR_PERMISSION_CHECK := true

# NFC
BOARD_NFC_CHIPSET := pn547
BOARD_NFC_HAL_SUFFIX := $(TARGET_BOARD_PLATFORM)

# WEBGL
ENABLE_WEBGL := true

# camera configs
USE_CAMERA_STUB := true
#zsl capture
TARGET_BOARD_CAMERA_CAPTURE_MODE := false
#back camera rotation capture
TARGET_BOARD_BACK_CAMERA_ROTATION := false
#front camera rotation capture
TARGET_BOARD_FRONT_CAMERA_ROTATION := false
#rotation capture
TARGET_BOARD_CAMERA_ROTATION_CAPTURE := false
TARGET_BOARD_CAMERA_HAL_VERSION := HAL3.0
TARGET_BOARD_CAMERA_ISP_SOFTWARE_VERSION := 2

# Audio
BOARD_USES_TINYALSA_AUDIO := true

# Charger
BACKLIGHT_PATH := "/sys/class/backlight/panel/brightness"
BOARD_CHARGER_ENABLE_SUSPEND := true
BOARD_NO_CHARGER_LED := true
HAVE_KEYBOARD_BACKLIGHT := false
BOARD_CHARGING_MODE_BOOTING_LPM := "/sys/class/power_supply/battery/batt_lp_charging"
CHARGING_ENABLED_PATH := "/sys/class/power_supply/battery/batt_lp_charging"

# zygote whitelist
PATH_WHITELIST_EXTRA_H := "/sys/devices/sec-battery.4/power_supply/battery/batt_lp_charging"

# Bluetooth
BOARD_HAVE_BLUETOOTH := true
SPRD_WCNBT_CHISET := marlin
BOARD_SPRD_WCNBT_MARLIN := true

BOARD_BLUETOOTH_BDROID_BUILDCFG_INCLUDE_DIR := device/samsung/j3xnlte/configs/bluetooth
BOARD_BLUEDROID_VENDOR_CONF := device/samsung/j3xnlte/configs/bluetooth/libbt_vndcfg.txt

# WIFI configs
BOARD_WPA_SUPPLICANT_DRIVER := NL80211
WPA_SUPPLICANT_VERSION      := VER_2_1_DEVEL
BOARD_WPA_SUPPLICANT_PRIVATE_LIB := lib_driver_cmd_sprdwl
BOARD_HOSTAPD_DRIVER        := NL80211
BOARD_HOSTAPD_PRIVATE_LIB   := lib_driver_cmd_sprdwl
BOARD_WLAN_DEVICE           := sc2341
WIFI_DRIVER_FW_PATH_PARAM   := "/data/misc/wifi/fwpath"
WIFI_DRIVER_FW_PATH_STA     := "sta_mode"
WIFI_DRIVER_FW_PATH_P2P     := "p2p_mode"
WIFI_DRIVER_FW_PATH_AP      := "ap_mode"
WIFI_DRIVER_MODULE_PATH     := "/system/lib/modules/sprdwl.ko"
WIFI_DRIVER_MODULE_NAME     := "sprdwl"

# Include an expanded selection of fonts
EXTENDED_FONT_FOOTPRINT := true

# SPRD
# TARGET_USE_SPRD_JEMALLOC := true

# DRM
BOARD_WIDEVINE_OEMCRYPTO_LEVEL := 3

# Dex
ifeq ($(HOST_OS),linux)
  ifeq ($(TARGET_BUILD_VARIANT),user)
    ifeq ($(WITH_DEXPREOPT),)
      WITH_DEXPREOPT := true
    endif
  endif
endif

# SELinux
BOARD_SEPOLICY_DIRS += \
    device/samsung/j3xnlte/sepolicy

# build.prop
TARGET_SYSTEM_PROP := device/samsung/j3xnlte/system.prop

#######################################################
# TWRP
RECOVERY_VARIANT := twrp

# Display
TW_THEME := portrait_hdpi
TARGET_RECOVERY_PIXEL_FORMAT := "ABGR_8888"
RECOVERY_GRAPHICS_FORCE_SINGLE_BUFFER := true
TW_MAX_BRIGHTNESS := 255
TW_DEFAULT_BRIGHTNESS := 162

# Storage
RECOVERY_SDCARD_ON_DATA := true

# Keys
BOARD_CUSTOM_RECOVERY_KEYMAPPING := ../../device/samsung/j3xnlte/recovery/recovery_keys.c
BOARD_HAS_NO_SELECT_BUTTON := true

# Crypto.
TW_INCLUDE_CRYPTO := true

# Misc.
TW_BRIGHTNESS_PATH := "/sys/class/backlight/panel/brightness"
TW_NO_REBOOT_BOOTLOADER := true
TW_HAS_DOWNLOAD_MODE := true
TW_NO_EXFAT_FUSE := true
TW_NEW_ION_HEAP := true
TW_MTP_DEVICE := "/dev/mtp_usb"
TW_EXCLUDE_SUPERSU := true
TW_CUSTOM_CPU_TEMP_PATH := "/sys/class/thermal/thermal_zone1/temp"
BOARD_RECOVERY_SWIPE := true

# twrp recovery fstab
ifeq ($(RECOVERY_VARIANT), twrp)
PRODUCT_COPY_FILES += device/samsung/j3xnlte/twrp.fstab:recovery/root/etc/twrp.fstab
endif

