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
possible_dtb_dirs = $(KERNEL_OUT)/arch/$(KERNEL_ARCH)/boot/dts/ $(KERNEL_OUT)/arch/$(KERNEL_ARCH)/boot/ 
dtb_dir = $(firstword $(wildcard $(possible_dtb_dirs)))

define build-dtimage-target
    $(call pretty,"Target dt image: $@")
    $(hide) $(DTBTOOL) $(BOARD_DTBTOOL_ARGS) -o $@ -s $(BOARD_KERNEL_PAGESIZE) -p $(KERNEL_OUT)/scripts/dtc/ $(dtb_dir)
    $(hide) chmod a+r $@
endef

$(INSTALLED_DTIMAGE_TARGET): $(DTBTOOL) $(INSTALLED_KERNEL_TARGET)
	$(build-dtimage-target)
	@echo -e ${CL_CYN}"Made DT image: $@"${CL_RST}


else
INSTALLED_DTIMAGE_TARGET := $(TARGET_PREBUILT_DTB)
endif

BOARD_MKBOOTIMG_ARGS += --dt $(INSTALLED_DTIMAGE_TARGET)

$(INSTALLED_BOOTIMAGE_TARGET): $(MKBOOTIMG) $(INSTALLED_DTIMAGE_TARGET) $(INTERNAL_BOOTIMAGE_FILES) $(BOOTIMAGE_EXTRA_DEPS)
	@echo -e ${CL_GRN}"----- Making boot image ------"${CL_RST}
	$(hide) $(MKBOOTIMG) $(INTERNAL_BOOTIMAGE_ARGS) $(BOARD_MKBOOTIMG_ARGS) --output $@
	@echo -e ${CL_CYN}"Made boot image: $@"${CL_RST}
	@echo -e ${CL_GRN}"----- Lying about SEAndroid state to Samsung bootloader ------"${CL_RST}
	$(hide) echo -n "SEANDROIDENFORCE" >> $(INSTALLED_BOOTIMAGE_TARGET)
	$(hide) $(call assert-max-image-size,$@,$(BOARD_BOOTIMAGE_PARTITION_SIZE),raw)

$(INSTALLED_RECOVERYIMAGE_TARGET): $(MKBOOTIMG) $(INSTALLED_DTIMAGE_TARGET) $(recovery_kernel) $(recovery_ramdisk)
	@echo -e ${CL_GRN}"----- Making recovery image ------"${CL_RST}
	$(hide) $(MKBOOTIMG) $(INTERNAL_RECOVERYIMAGE_ARGS) $(BOARD_MKBOOTIMG_ARGS) --output $@ --ramdisk $(recovery_ramdisk)
	@echo -e ${CL_CYN}"Made recovery image: $@"${CL_RST}
	@echo -e ${CL_GRN}"----- Lying about SEAndroid state to Samsung bootloader ------"${CL_RST}
	$(hide) echo -n "SEANDROIDENFORCE" >> $(INSTALLED_RECOVERYIMAGE_TARGET)
	$(hide) $(call assert-max-image-size,$@,$(BOARD_RECOVERYIMAGE_PARTITION_SIZE),raw)
	$(hide) tar -C $(PRODUCT_OUT) -H ustar -c recovery.img > $(FLASH_IMAGE_TARGET)
	@echo -e ${CL_CYN}"Made Odin flashable recovery tar: ${FLASH_IMAGE_TARGET}"${CL_RST}

