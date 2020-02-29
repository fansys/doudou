package com.doudou.wx.api.util;

import com.alibaba.fastjson.JSONObject;
import com.doudou.core.constant.WxApiConstant;
import com.doudou.wx.api.exception.WxApiException;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author ljh
 */
@Slf4j
public class WeChatUtils {

    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.getDecoder().decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.getDecoder().decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.getDecoder().decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];

                Arrays.fill(temp, (byte) 0);

                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);

                keyByte = temp;

            }

            // 初始化

            Security.addProvider(new BouncyCastleProvider());

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");

            parameters.init(new IvParameterSpec(ivByte));

            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);

            byte[] resultByte = cipher.doFinal(dataByte);

            if (null != resultByte && resultByte.length > 0) {

                String result = new String(resultByte, "UTF-8");

                return JSONObject.parseObject(result);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public static JSONObject requestToWx(String requestUrl) {
        log.info("requestUrl : [{}]",requestUrl);
        String response = HttpRequest.get(requestUrl).body();
        log.info("response : [{}]",response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        if (StringUtils.isNotBlank(jsonObject.getString(WxApiConstant.WX_ERROR_CODE))) {
            throw new WxApiException(jsonObject.getString(WxApiConstant.WX_ERROR_MSG));
        }
        return jsonObject;
    }


}