# Release name
PRODUCT_RELEASE_NAME := j2xlte

# Inherit some common CM stuff.
$(call inherit-product, vendor/cm/config/common_full_phone.mk)

# Inherit device configuration
$(call inherit-product, device/samsung/j2xlte/device_j2xlte.mk)

## Device identifier. This must come after all inclusions
PRODUCT_DEVICE := j2xlte
PRODUCT_NAME := lineage_j2xlte
PRODUCT_BRAND := samsung
PRODUCT_MODEL := SM-J210F
PRODUCT_MANUFACTURER := samsung
