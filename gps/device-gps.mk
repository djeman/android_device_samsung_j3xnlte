
LOCAL_PATH := device/samsung/j3xnlte/gps

PRODUCT_COPY_FILES += \
	$(LOCAL_PATH)/gpsd:system/bin/gpsd \
	$(LOCAL_PATH)/gps.default.so:system/lib/hw/gps.default.so \
	$(LOCAL_PATH)/gps.xml:system/etc/gps.xml \
	$(LOCAL_PATH)/libwrappergps.so:system/lib/libwrappergps.so \
	$(LOCAL_PATH)/gps.cer:system/bin/gps.cer

