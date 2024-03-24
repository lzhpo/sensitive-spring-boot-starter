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
package com.lzhpo.sensitive.test.encrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import java.security.KeyPair;
import org.junit.jupiter.api.Test;

class Rsa {

    /**
     * privateKey: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANyhI/9e5evQyzRLVsUmbQesdRl7fXu9ZAl6lZqVyL+ypf6FmQouH89OTEv/JjmybuAha9zsYwNAKSobSRATaqYSCvdwoPRgUFRFfq6ed61kpO5+D+T/X3v85JmXIngkieCe9n5b5KT3XNtHFBXVsZ3/onWEYZRhFMTsMsKkvijBAgMBAAECgYAKV2fEbC5vAp0JvRfKuym8ZLgi6wPHWWnfW154jdmIab9n2huBq4aMbSU8oS+pn+xcR1jC1NYxxG/BhGCk9yIGIzE/57tggjibNpiqC/uS12SiaJPz9oqOVJPI+l5uf9xqdytzvNJe6AGMViZdS+nnQRZfdDrs5cgghv7lx+kjiQJBAOQWmEJukHaIUXvW8ZWNekIgb8/Frq7gNvRaeqjqpZMqUIXXDj80eODGsNjIUwwEdlFX4//C7udmLfWfhyOq1bkCQQD3oOGP8rjIkouhbJldaILeuaN3ee3v3dtsmLM8epC9HH3EcFBD2O+l60wCa67uM/ArPn3XjL/lidqnVAJHPG9JAkEAumz1WicAkMFuyGew4enXKcFVYl9THcBJaoOhifrwBk8prZtPG74Jpr7/wNBLgKENDANoaZ2soxnTKtWPIUn6kQJAAmcxSTBV0rx5VmuzYVCuVHMAvxwTzwwcIQWqV5/o36zzG4Drhn0Idle+ORfKbs1aO1Ez72+SPSwFTzJlg0N24QJATQu2dlhbm87uGh0fUHpV6Nw6lf/mBMek1stC8PQXB0MtNPeYd+Ul45zfc+k5mIWUHwt47To5uAo2ywsCSdWBCw==
     * publicKey: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcoSP/XuXr0Ms0S1bFJm0HrHUZe317vWQJepWalci/sqX+hZkKLh/PTkxL/yY5sm7gIWvc7GMDQCkqG0kQE2qmEgr3cKD0YFBURX6unnetZKTufg/k/197/OSZlyJ4JIngnvZ+W+Sk91zbRxQV1bGd/6J1hGGUYRTE7DLCpL4owQIDAQAB
     */
    @Test
    void generateRsaKey() {
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");

        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        System.out.println("privateKey: " + privateKey);

        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
        System.out.println("publicKey: " + publicKey);
    }
}
