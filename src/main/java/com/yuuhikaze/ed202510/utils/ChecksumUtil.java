package com.yuuhikaze.ed202510.utils;

import java.util.zip.CRC32;

public class ChecksumUtil {

    public static long computeCRC32(Object data) {
        CRC32 crc = new CRC32();
        crc.update(data.toString().getBytes());
        return crc.getValue();
    }
}
