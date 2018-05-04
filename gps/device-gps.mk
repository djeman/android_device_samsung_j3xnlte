
LOCAL_PATH := device/samsung/j3xnlte/gps

PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/gpsd:$(TARGET_COPY_OUT_VENDOR)/bin/gpsd \
	$(LOCAL_PATH)/gps.default.so:$(TARGET_COPY_OUT_VENDOR)/lib/hw/gps.default.so \
	$(LOCAL_PATH)/gps.xml:$(TARGET_COPY_OUT_VENDOR)/etc/gps.xml \
	$(LOCAL_PATH)/libwrappergps.so:$(TARGET_COPY_OUT_VENDOR)/lib/libwrappergps.so \
	$(LOCAL_PATH)/gps.cer:$(TARGET_COPY_OUT_VENDOR)/bin/gps.cer

