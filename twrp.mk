# Copyright (C) 2017 The LineageOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

TARGET_RECOVERY_DEVICE_DIRS += device/samsung/j3xnlte/twrp

# Display
TW_THEME := portrait_hdpi
TARGET_RECOVERY_PIXEL_FORMAT := "ABGR_8888"
RECOVERY_GRAPHICS_FORCE_SINGLE_BUFFER := true
TW_MAX_BRIGHTNESS := 255
TW_DEFAULT_BRIGHTNESS := 162

# Storage
RECOVERY_SDCARD_ON_DATA := true

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
