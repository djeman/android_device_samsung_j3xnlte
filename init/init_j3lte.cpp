/*
   Copyright (c) 2013, The Linux Foundation. All rights reserved.
   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are
   met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.
    * Neither the name of The Linux Foundation nor the names of its
      contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.
   THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
   WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
   ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
   BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
   BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
   WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
   OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
   IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include <stdlib.h>

#include "vendor_init.h"
#include "property_service.h"
#include "log.h"
#include "util.h"

#define ISMATCH(a,b) (!strncmp(a,b,PROP_VALUE_MAX))

static void import_kernel_hwrev(const std::string& key, const std::string& value, bool for_emulator)
{
    if (key.empty()) return;

    if (key == "hw_revision") {
        property_set("ro.revision", value.c_str());
    }
}

void vendor_load_properties()
{
    std::string platform = property_get("ro.board.platform");
    if (platform.empty() || platform != ANDROID_TARGET)
        return;

    import_kernel_cmdline(false, import_kernel_hwrev);
    std::string revision = property_get("ro.revision");
    if (revision.empty())
        property_set("ro.revision", "0");
    
    char* simslot_count_path = (char *)"/proc/simslot_count";
    // Create a two byte array, so we can use it as a string
    char simslot_count[2] = "\0";
    
    // Open the file as read-only
    FILE* file = fopen(simslot_count_path, "r");
    if (file != NULL) {
        simslot_count[0] = fgetc(file);
        property_set("ro.multisim.simslotcount", simslot_count);
        property_set("ro.msms.phone_count", simslot_count);
        property_set("ro.modem.w.count", simslot_count);
        property_set("persist.msms.phone_count", simslot_count);
        if (ISMATCH(simslot_count, "2"))
            property_set("persist.radio.multisim.config", "dsds");

        fclose(file);
    }

    std::string bootloader = property_get("ro.bootloader");
    if (strstr(bootloader.c_str(), "J320FN")) {
        /* SM-J320FN */
        property_set("ro.product.model", "SM-J320FN");
        property_set("ro.product.device", "j3xnlte");
    } else {
        property_set("ro.product.model", "SM-J320F");
        property_set("ro.product.device", "j3xlte");
    }

    std::string device = property_get("ro.product.device");
    INFO("Found bootloader id %s setting build properties for %s device\n", bootloader.c_str(), device.c_str());
}
