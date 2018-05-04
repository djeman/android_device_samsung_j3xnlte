#include <sys/types.h>

// Android O exports
extern "C" ssize_t _ZN7android13SensorManager13getSensorListEPPKPKNS_6SensorE(void* thiz, void* list);

/*
 * FUNCTION: android::SensorManager::getSensorList(Sensor const* const** list)
 * USE:      Get a list of sensors?
 * NOTES:    It looks like this function was renamed in N. Stub out to the correct call.
 */
extern "C" ssize_t _ZNK7android13SensorManager13getSensorListEPPKPKNS_6SensorE(void* thiz, void* list)
{
    return _ZN7android13SensorManager13getSensorListEPPKPKNS_6SensorE(thiz, list);
}

