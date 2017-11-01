/* //device/system/rild/rild.c
**
** Copyright 2006 The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>
#include <string.h>
#include <stdint.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

#include <telephony/ril.h>
#define LOG_TAG "RILD"
#include <utils/Log.h>
#include <cutils/properties.h>
#include <cutils/sockets.h>
#include <sys/capability.h>
#include <sys/prctl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <libril/ril_ex.h>

#include <private/android_filesystem_config.h>

#define LIB_PATH_PROPERTY   "rild.libpath"
#define LIB_ARGS_PROPERTY   "rild.libargs"
#define MAX_LIB_ARGS        16
#define MAX_CAP_NUM         (CAP_TO_INDEX(CAP_LAST_CAP) + 1)

#define LIB_PATH_PROPERTY_2      "rild.libpath2"
#define SIM_COUNT_PROPERTY       "ro.multisim.simslotcount"
#define STATUS_DAEMON1_PROPERTY  "init.svc.ril-daemon1"
#define DAEMON1_SERVICE_NAME     "ril-daemon1"

#define START_SERVICE_PROPERTY   "ctl.start"
#define STOP_SERVICE_PROPERTY    "ctl.stop"

static void usage(const char *argv0) {
    fprintf(stderr, "Usage: %s -l <ril impl library> [-- <args for impl library>]\n", argv0);
    exit(EXIT_FAILURE);
}

extern char rild[MAX_SOCKET_NAME_LENGTH] __attribute__((weak));

extern void RIL_register (const RIL_RadioFunctions *callbacks, RIL_SOCKET_ID socket_id);

extern void RIL_register_socket (RIL_RadioFunctions *(*rilUimInit)
        (const struct RIL_Env *, int, char **), RIL_SOCKET_TYPE socketType, 
        int argc, char **argv, RIL_SOCKET_ID socket_id);

extern void RIL_onRequestComplete(RIL_Token t, RIL_Errno e,
        void *response, size_t responselen);

extern void RIL_setRilSocketName(char *);

extern void RIL_onUnsolicitedResponse(int unsolResponse, const void *data,
        size_t datalen, RIL_SOCKET_ID socket_id);

extern void RIL_requestTimedCallback (RIL_TimedCallback callback,
        void *param, const struct timeval *relativeTime);

extern void RIL_setRilSocketName(char * s) __attribute__((weak));

static struct RIL_Env s_rilEnv = {
    RIL_onRequestComplete,
    RIL_onUnsolicitedResponse,
    RIL_requestTimedCallback
};

extern void RIL_startEventLoop();

static int make_argv(char * args, char ** argv) {
    // Note: reserve argv[0]
    int count = 1;
    char * tok;
    char * s = args;

    while ((tok = strtok(s, " \0"))) {
        argv[count] = tok;
        s = NULL;
        count++;
    }
    return count;
}

/*
 * switchUser - Switches UID to radio, preserving CAP_NET_ADMIN capabilities.
 * Our group, cache, was set by init.
 */
void switchUser() {
    char debuggable[PROP_VALUE_MAX];

    prctl(PR_SET_KEEPCAPS, 1, 0, 0, 0);
    if (setresuid(AID_RADIO, AID_RADIO, AID_RADIO) == -1) {
        RLOGE("setresuid failed: %s", strerror(errno));
        exit(EXIT_FAILURE);
    }

    struct __user_cap_header_struct header;
    memset(&header, 0, sizeof(header));
    header.version = _LINUX_CAPABILITY_VERSION_3;
    header.pid = 0;

    struct __user_cap_data_struct data[MAX_CAP_NUM];
    memset(&data, 0, sizeof(data));

    data[CAP_TO_INDEX(CAP_NET_ADMIN)].effective |= CAP_TO_MASK(CAP_NET_ADMIN);
    data[CAP_TO_INDEX(CAP_NET_ADMIN)].permitted |= CAP_TO_MASK(CAP_NET_ADMIN);

    data[CAP_TO_INDEX(CAP_NET_RAW)].effective |= CAP_TO_MASK(CAP_NET_RAW);
    data[CAP_TO_INDEX(CAP_NET_RAW)].permitted |= CAP_TO_MASK(CAP_NET_RAW);

    data[CAP_TO_INDEX(CAP_BLOCK_SUSPEND)].effective |= CAP_TO_MASK(CAP_BLOCK_SUSPEND);
    data[CAP_TO_INDEX(CAP_BLOCK_SUSPEND)].permitted |= CAP_TO_MASK(CAP_BLOCK_SUSPEND);

    if (capset(&header, &data[0]) == -1) {
        RLOGE("capset failed: %s", strerror(errno));
        exit(EXIT_FAILURE);
    }

    /*
     * Debuggable build only:
     * Set DUMPABLE that was cleared by setuid() to have tombstone on RIL crash
     */
    property_get("ro.debuggable", debuggable, "0");
    if (strcmp(debuggable, "1") == 0) {
        prctl(PR_SET_DUMPABLE, 1, 0, 0, 0);
    }
}

int main(int argc, char **argv) {
    const char * rilLibPath = NULL;
    char **rilArgv;
    void *dlHandle;
    const RIL_RadioFunctions *(*rilInit)(const struct RIL_Env *, int, char **);
    RIL_RadioFunctions *(*rilUimInit)(const struct RIL_Env *, int, char **);
    const char *err_str = NULL;

    const RIL_RadioFunctions *funcs;
    char libPath[PROPERTY_VALUE_MAX];
    unsigned char hasLibArgs = 0;
    RIL_SOCKET_ID socket_id;
    char simCount[PROPERTY_VALUE_MAX];
    char dstate[PROPERTY_VALUE_MAX];

    int i;
    const char *clientId = NULL;
    RLOGD("**RIL Daemon Started**");
    RLOGD("**RILd param count=%d**", argc);

    umask(S_IRGRP | S_IWGRP | S_IXGRP | S_IROTH | S_IWOTH | S_IXOTH);
    for (i = 1; i < argc ;) {
        if (0 == strcmp(argv[i], "-l") && (argc - i > 1)) {
            rilLibPath = argv[i + 1];
            i += 2;
        } else if (0 == strcmp(argv[i], "--")) {
            i++;
            hasLibArgs = 1;
            break;
        } else if (0 == strcmp(argv[i], "-c") &&  (argc - i > 1)) {
            clientId = argv[i+1];
            i += 2;
        } else {
            usage(argv[0]);
        }
    }

    if (clientId == NULL) {
        clientId = "0";
    } else if (atoi(clientId) > MAX_RILDS) {
        RLOGE("Max Number of rild's supported is: %d", MAX_RILDS);
        exit(0);
    }

    if (strncmp(clientId, "2", MAX_CLIENT_ID_LENGTH) == 0) {
        strlcat(rild, clientId, MAX_SOCKET_NAME_LENGTH);
        RIL_setRilSocketName(rild);
        socket_id = RIL_SOCKET_2;
    } else if (strncmp(clientId, "3", MAX_CLIENT_ID_LENGTH) == 0) {
        strlcat(rild, clientId, MAX_SOCKET_NAME_LENGTH);
        RIL_setRilSocketName(rild);
        socket_id = RIL_SOCKET_3;
    } else if (strncmp(clientId, "4", MAX_CLIENT_ID_LENGTH) == 0) {
        strlcat(rild, clientId, MAX_SOCKET_NAME_LENGTH);
        RIL_setRilSocketName(rild);
        socket_id = RIL_SOCKET_4;
    } else {
        socket_id = RIL_SOCKET_1;
    }

    if (rilLibPath == NULL) {
        if ( 0 == property_get(LIB_PATH_PROPERTY, libPath, NULL)) {
            // No lib sepcified on the command line, and nothing set in props.
            // Assume "no-ril" case.
            goto done;
        }

        if (socket_id == RIL_SOCKET_2) {
            property_get(LIB_PATH_PROPERTY_2, libPath, NULL);
        }

        rilLibPath = libPath;
    }

    RLOGD("rilLibPath = %s", rilLibPath);

    switchUser();

    dlHandle = dlopen(rilLibPath, RTLD_NOW);

    if (dlHandle == NULL) {
        RLOGE("dlopen failed: %s", dlerror());
        exit(EXIT_FAILURE);
    }

    RIL_startEventLoop();

    rilInit =
        (const RIL_RadioFunctions *(*)(const struct RIL_Env *, int, char **))
        dlsym(dlHandle, "RIL_Init");

    if (rilInit == NULL) {
        RLOGE("RIL_Init not defined or exported in %s\n", rilLibPath);
        exit(EXIT_FAILURE);
    }

    dlerror(); // Clear any previous dlerror
    rilUimInit =
        (RIL_RadioFunctions *(*)(const struct RIL_Env *, int, char **))
        dlsym(dlHandle, "RIL_SAP_Init");
    err_str = dlerror();
    if (err_str) {
        RLOGW("RIL_SAP_Init not defined or exported in %s: %s\n", rilLibPath, err_str);
    } else if (!rilUimInit) {
        RLOGW("RIL_SAP_Init defined as null in %s. SAP Not usable\n", rilLibPath);
    }

    if (hasLibArgs) {
        rilArgv = argv + i - 1;
        argc = argc -i + 1;
    } else {
        static char * newArgv[MAX_LIB_ARGS];
        static char args[PROPERTY_VALUE_MAX];
        rilArgv = newArgv;
        property_get(LIB_ARGS_PROPERTY, args, "");
        argc = make_argv(args, rilArgv);
    }

    rilArgv[argc++] = "-c";
    rilArgv[argc++] = clientId;
    RLOGD("RIL_Init argc = %d clientId = %s", argc, rilArgv[argc-1]);

    // Make sure there's a reasonable argv[0]
    rilArgv[0] = argv[0];

    funcs = rilInit(&s_rilEnv, argc, rilArgv);
    RLOGD("RIL_Init rilInit completed");

    RIL_register(funcs, socket_id);

    RLOGD("RIL_Init RIL_register completed");

    if (rilUimInit) {
        RLOGD("RIL_register_socket started");
        RIL_register_socket(rilUimInit, RIL_SAP_SOCKET, argc, rilArgv, socket_id);
    }

    RLOGD("RIL_register_socket completed");

    property_get(SIM_COUNT_PROPERTY, simCount, NULL);
    if (simCount[0] == '2' && socket_id == RIL_SOCKET_1) {
        property_get(STATUS_DAEMON1_PROPERTY, dstate, NULL);
        if (strncmp(dstate, "running", 7) == 0) {
            RLOGD("RIL_Init stop ril-daemon1");
            property_set(STOP_SERVICE_PROPERTY, DAEMON1_SERVICE_NAME);
            sleep(1);
        }

        RLOGD("RIL_Init start ril-daemon1");
        property_set(START_SERVICE_PROPERTY, DAEMON1_SERVICE_NAME);
    }

done:

    RLOGD("RIL_Init starting sleep loop");
    while (true) {
        sleep(UINT32_MAX);
    }
}
