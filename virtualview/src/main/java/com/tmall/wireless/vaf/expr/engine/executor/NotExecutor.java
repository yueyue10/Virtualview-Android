/*
 * MIT License
 *
 * Copyright (c) 2018 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tmall.wireless.vaf.expr.engine.executor;

import android.text.TextUtils;
import com.socks.library.KLog;

import com.tmall.wireless.vaf.expr.engine.data.Data;

/**
 * Created by gujicheng on 16/9/18.
 */
public class NotExecutor extends ArithExecutor {
    private final static String TAG = "NotExecutor_TMTEST";

    @Override
    public int execute(Object com) {
        int ret = super.execute(com);

        byte type = mCodeReader.readByte();
//        KLog.d(TAG, "execute type:" + type);

        Data data = readData(type);
        if (TYPE_Var == type) {
            mAriResultRegIndex = mCodeReader.readByte();
        }
        Data result = mRegisterManger.get(mAriResultRegIndex);

//        KLog.d(TAG, "xxx data:" + data + " result:" + result);
        if (null != data && null != result) {
            ret = RESULT_STATE_SUCCESSFUL;
            switch (data.mType) {
                case Data.TYPE_INT:
                    result.setInt((0 == data.getInt()) ? 1 : 0);
                    break;

                case Data.TYPE_FLOAT:
                    result.setInt((0 == data.getFloat()) ? 1 : 0);
                    break;

                case Data.TYPE_STR:
                    result.setInt((TextUtils.isEmpty(data.getString())) ? 1 : 0);
                    break;

                default:
                    ret = RESULT_STATE_ERROR;
                    KLog.e(TAG, "invalidate type:" + data.mType);
                    break;
            }
        } else {
            KLog.e(TAG, "read data failed");
        }

        return ret;
    }
}
