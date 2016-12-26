/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lh16808.app.lhys.utils.update;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateInfo {
    // 是否有新版本
    public boolean hasUpdate = false;
    // 是否静默下载：有新版本时不提示直接下载
    public boolean isSilent = false;
    // 是否强制安装：不安装无法使用app
    public boolean isForce = false;
    // 是否下载完成后自动安装
    public boolean isAutoInstall = true;
    // 是否可忽略该版本
    public boolean isIgnorable = true;

    // 是否可忽略该版本
    public boolean isWifiOnly = true;

    public int versionCode;
    public String versionName;

    public String updateContent;

    public String url;
    public long size;

    public String name;
    public String qrcode1;
    public int ioson;
    public String sharetext;
    public String sharelink;
    public static boolean isSeting;
    public String fileName = "lh";

    public static UpdateInfo parse(String s) throws JSONException {
        JSONObject o = new JSONObject(s);
        return parse(o);
    }

    private static UpdateInfo parse(JSONObject o) {
        UpdateInfo info = new UpdateInfo();
        if (o == null) {
            return info;
        }
        info.qrcode1 = o.optString("qrcode");
        info.url = o.optString("qrurl");
        info.versionCode = o.optInt("vo", 0);
        info.versionName = o.optString("version");
        info.updateContent = o.optString("feature");
        info.sharetext = o.optString("sharetext");
        info.sharelink = o.optString("sharelink");
        info.size = o.optLong("filelen", 0);
        info.ioson = o.optInt("ioson");
        info.name="六合运势";
        return info;
    }
}