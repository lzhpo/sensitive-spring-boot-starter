/*
 * Copyright lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzhpo.sensitive;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "sensitive")
public class SensitiveProperties {

    @NestedConfigurationProperty
    private Encrypt encrypt;

    @Data
    public static class Encrypt {

        @NestedConfigurationProperty
        private Aes aes;

        @NestedConfigurationProperty
        private Des des;

        @NestedConfigurationProperty
        private Rsa rsa;
    }

    @Data
    public static class Aes {
        private String key;
    }

    @Data
    public static class Des {
        private String key;
    }

    @Data
    public static class Rsa {

        /**
         * Get RSA private key.
         * <pre>
         *     KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
         *     String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
         * </pre>
         */
        private String privateKey;

        /**
         * Get RSA public key.
         * <pre>
         *     KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
         *     String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
         * </pre>
         */
        private String publicKey;
    }
}
