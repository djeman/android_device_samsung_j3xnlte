LOCAL_PATH := $(call my-dir)

FLASH_IMAGE_TARGET ?= $(PRODUCT_OUT)/recovery.tar

INSTALLED_BOOTIMAGE_TARGET := $(PRODUCT_OUT)/boot.img

#----------------------------------------------------------------------
# Generate device tree image (dt.img)
#----------------------------------------------------------------------

ifeq ($(strip $(TARGET_PREBUILT_DTB)),)

DTBTOOL_NAME := $(TARGET_CUSTOM_DTBTOOL)
DTBTOOL := $(HOST_OUT_EXECUTABLES)/$(DTBTOOL_NAME)$(HOST_EXECUTABLE_SUFFIX)

INSTALLED_DTIMAGE_TARGET := $(PRODUCT_OUT)/dt.img

# Most specific paths must come first in possible_dtb_dirs
DTB_DIR = $(KERNEL_OUT)/arch/$(KERNEL_ARCH)/boot/dts/

define build-dtimage-target
    $(call pretty,"Target dt image: $@")
    $(hide) $(DTBTOOL) $(BOARD_DTBTOOL_ARGS) -o $@ -s $(BOARD_KERNEL_PAGESIZE) -p $(KERNEL_OUT)/scripts/dtc/ $(DTB_DIR)
    $(hide) chmod a+r $@
endef

$(INSTALLED_DTIMAGE_TARGET): $(DTBTOOL) $(INSTALLED_KERNEL_TARGET)
	$(build-dtimage-target)
	@echo "Made DT image: $@"


else
INSTALLED_DTIMAGE_TARGET := $(TARGET_PREBUILT_DTB)
endif

BOARD_MKBOOTIMG_ARGS += --dt $(INSTALLED_DTIMAGE_TARGET)

$(INSTALLED_BOOTIMAGE_TARGET): $(MKBOOTIMG) $(INSTALLED_DTIMAGE_TARGET) $(INTERNAL_BOOTIMAGE_FILES) $(BOOTIMAGE_EXTRA_DEPS)
	@echo "----- Making boot image ------"
	$(hide) $(MKBOOTIMG) $(INTERNAL_BOOTIMAGE_ARGS) $(INTERNAL_MKBOOTIMG_VERSION_ARGS) $(BOARD_MKBOOTIMG_ARGS) --output $@
	@echo "Made boot image: $@"
	@echo "----- Lying about SEAndroid state to Samsung bootloader ------"
	$(hide) echo -n "SEANDROIDENFORCE" >> $@
	$(hide) $(call assert-max-image-size,$@,$(BOARD_BOOTIMAGE_PARTITION_SIZE),raw)

$(INSTALLED_RECOVERYIMAGE_TARGET): $(MKBOOTIMG) $(INSTALLED_DTIMAGE_TARGET) $(recovery_ramdisk) $(recovery_kernel)
	@echo "----- Making recovery image ------"
	$(hide) $(MKBOOTIMG) $(INTERNAL_RECOVERYIMAGE_ARGS) $(INTERNAL_MKBOOTIMG_VERSION_ARGS) $(BOARD_MKBOOTIMG_ARGS) --output $@
	@echo "Made recovery image: $@"
	@echo "----- Lying about SEAndroid state to Samsung bootloader ------"
	$(hide) echo -n "SEANDROIDENFORCE" >> $@
	$(hide) $(call assert-max-image-size,$@,$(BOARD_RECOVERYIMAGE_PARTITION_SIZE),raw)
	$(hide) tar -C $(PRODUCT_OUT) -H ustar -c recovery.img > $(FLASH_IMAGE_TARGET)
	@echo "Made Odin flashable recovery tar: ${FLASH_IMAGE_TARGET}"

