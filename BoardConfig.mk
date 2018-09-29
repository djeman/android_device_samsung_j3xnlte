#
# Copyright (C) 2016 The CyanogenMod Project
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
#

# inherit from samsung sharkls-common
-include device/samsung/sharkls-common/BoardConfigCommon.mk

# kernel
#TARGET_KERNEL_CONFIG := j3xnlte_defconfig
TARGET_KERNEL_CONFIG := j3xnlte_permissive_defconfig

# Init
TARGET_INIT_VENDOR_LIB := libinit_j3xnlte
TARGET_RECOVERY_DEVICE_MODULES := libinit_j3xnlte

# NFC
BOARD_NFC_CHIPSET := pn548
TARGET_USES_NQ_NFC := true

