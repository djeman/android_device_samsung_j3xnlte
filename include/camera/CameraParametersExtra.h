// Overload this file in your device specific config if you need
// to add extra camera parameters.
// A typical file would look like this:
/*
 * Copyright (C) 2014 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define CAMERA_PARAMETERS_EXTRA_C \
const char CameraParameters::KEY_BRIGHTNESS[] = "brightness"; \
const char CameraParameters::KEY_SUPPORTED_BRIGHTNESS[] = "brightness-values"; \
const char CameraParameters::KEY_CONTRAST[] = "contrast"; \
const char CameraParameters::KEY_SUPPORTED_CONTRAST[] = "contrast-values"; \
const char CameraParameters::KEY_SATURATION[] = "saturation"; \
const char CameraParameters::KEY_SUPPORTED_SATURATION[] = "saturation-values"; \
const char CameraParameters::KEY_SLOWMOTION[] = "slow-motion"; \
const char CameraParameters::KEY_SUPPORTED_SLOWMOTION[] = "slow-motion-values"; \
const char CameraParameters::KEY_METERING_MODE[] = "metering-mode"; \
const char CameraParameters::KEY_SUPPORTED_METERING_MODE[] = "metering-mode-values"; \
const char CameraParameters::KEY_SENSOR_ORIENT[] = "sensor-orient"; \
const char CameraParameters::KEY_SENSOR_ROT[] = "sensor-rot"; \
const char CameraParameters::KEY_ISO[] = "iso"; \
const char CameraParameters::KEY_SUPPORTED_ISO[] = "iso-values"; \
const char CameraParameters::KEY_PERFECT_SKIN_LEVEL[] = "perfect-skin-level";

#define CAMERA_PARAMETERS_EXTRA_H \
    static const char KEY_BRIGHTNESS[]; \
    static const char KEY_SUPPORTED_BRIGHTNESS[]; \
    static const char KEY_CONTRAST[]; \
    static const char KEY_SUPPORTED_CONTRAST[]; \
    static const char KEY_SATURATION[]; \
    static const char KEY_SUPPORTED_SATURATION[]; \
    static const char KEY_SLOWMOTION[]; \
    static const char KEY_SUPPORTED_SLOWMOTION[]; \
    static const char KEY_METERING_MODE[]; \
    static const char KEY_SUPPORTED_METERING_MODE[]; \
    static const char KEY_SENSOR_ORIENT[]; \
    static const char KEY_SENSOR_ROT[]; \
    static const char KEY_ISO[]; \
    static const char KEY_SUPPORTED_ISO[]; \
    static const char KEY_PERFECT_SKIN_LEVEL[];

