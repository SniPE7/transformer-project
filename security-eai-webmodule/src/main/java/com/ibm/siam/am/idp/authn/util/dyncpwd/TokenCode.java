package com.ibm.siam.am.idp.authn.util.dyncpwd;


public class TokenCode{
     final int EXP = 255;
     byte hi[] = new byte[8];
     char bFirst = '\001';


    public  byte ror8(byte x, int n){
        return (byte)((x & 0xff) >>> (n & 0x7) | (x & 0xff) << (-n & 0x7));
    }

     void rol64_1(byte x[]){
        byte c0 = (byte)((x[0] & 0xff) >>> 7);
        for(int i = 7; i >= 0; i--){
            byte c1 = (byte)((x[i] & 0xff) >>> 7);
            x[i] = (byte)((x[i] & 0xff) << 1);
            x[i] = (byte)(x[i] & 0xff | c0);
            c0 = c1;
        }
    }

     void securid_expand_key_to_4_bit_per_byte(byte key[], byte target[]){
        for(int i = 0; i < 8; i++){
            target[i << 1] = (byte)((key[i] & 0xff) >> 4);
            target[(i << 1) + 1] = (byte)(key[i] & 0xf);
        }
    }

     void securid_expand_data_to_1_bit_per_byte(byte source[], byte target[]){
        int i = 0;
        int k = 0;
        for(; i < 8; i++){
            for(int j = 7; j >= 0; j--)
                target[k++] = (byte)((source[i] & 0xff) >> j & 0x1);
        }
    }

     void securid_reassemble_64_bit_from_64_byte(byte source[], byte target[]) {
        int k = 0;
        for(int i = 0; i < 8; i++){
            target[i] = 0;
            for(int j = 7; j >= 0; j--)
                target[i] = (byte)(target[i] & 0xff | (source[k++] & 0xff) << j);

        }
    }

     void securid_permute_data(byte data[], byte key[]){
        byte bit_data[] = new byte[128];
        byte hex_key[] = new byte[16];
        int hkw_flag = 0;
        int permuted_bit_flag = 0;
        securid_expand_data_to_1_bit_per_byte(data, bit_data);
        securid_expand_key_to_4_bit_per_byte(key, hex_key);
        byte permuted_bit[] = bit_data;
        long bit = 32L;
        byte hkw[] = hex_key;
        long m = 0L;
        for(; bit <= 32L && bit >= 0L; bit -= 32L) {
            permuted_bit_flag = (int)(64L + bit);
            long k = 0L;
            for(long b = 28L; k < 8L; b -= 4L) {
                for(byte j = hkw[(int)((long)hkw_flag + k)]; (j & 0xff) > 0; j = (byte)((j & 0xff) - 1)) {
                    byte c = (byte)(int)(bit + b + m + 4L & 63L);
                    bit_data[c] = bit_data[(int)m];
                    m = m + 1L & 63L;
                }

                for(long i = 0L; i < 4L; i++)
                    permuted_bit[(int)((long)permuted_bit_flag + b + i)] |= bit_data[(int)(bit + b + m + i & 63L)];
                k++;
            }
            hkw_flag += 8;
        }

        byte bit_data_temp[] = new byte[64];
        System.arraycopy(bit_data, 64, bit_data_temp, 0, 64);
        securid_reassemble_64_bit_from_64_byte(bit_data_temp, data);
        System.arraycopy(bit_data_temp, 0, bit_data, 64, 64);
    }

     void securid_do_4_rounds(byte data[], byte key[]) {
        for(byte round = 0; round < 4; round++){
            for(byte i = 0; i < 8; i++) {
                for(byte j = 0; j < 8; j++){
                    if((((key[i] & 0xff) >> (j ^ 0x7) ^ (data[0] & 0xff) >> 7) & 0x1) != 0) {
                        byte t = data[4];
                        data[4] = (byte)(100 - (data[0] & 0xff));
                        data[0] = t;
                    } else{
                        data[0] = (byte)((byte)(ror8((byte)(ror8(data[0], 1) - 1), 1) - 1) ^ data[4]);
                    }
                    rol64_1(data);
                }
            }
            for(int n = 0; n < 8; n++)
                key[n] ^= data[n];
        }
    }

     void securid_convert_to_decimal(byte data[], byte key[]){
        byte c = (byte)((key[7] & 0xf) % 5);
        for(long i = 0L; i < 8L; i++) {
            byte hi = (byte)((data[(int)i] & 0xff) >> 4);
            byte lo = (byte)(data[(int)i] & 0xf);
            c = (byte)((c + ((key[(int)i] & 0xff) >> 4)) % 5);
            if(hi > 9) {
                hi = (byte)(((hi & 0xff) - ((c + 1 & 0xff) << 1)) % 10);
                data[(int)i] = (byte)(hi << 4 | lo);
            }
            c = (byte)(((c & 0xff) + (key[(int)i] & 0xf)) % 5);
            if(lo > 9)
                data[(int)i] = (byte)((lo = (byte)(((lo & 0xff) - ((c + 1 & 0xff) << 1)) % 10)) | (hi & 0xff) << 4);
        }

    }

     void securid_hash_data(byte data[], byte key[]){
        securid_permute_data(data, key);
        securid_do_4_rounds(data, key);
        securid_permute_data(data, key);
        securid_convert_to_decimal(data, key);
    }

     void securid_hash_time(long time, byte hash[], byte key[]) {
        byte k[] = new byte[8];
        System.arraycopy(key, 0, k, 0, 8);
        hash[0] = (byte)(int)(time >>> 16);
        hash[1] = (byte)(int)(time >>> 8);
        hash[2] = (byte)(int)time;
        hash[3] = (byte)(int)time;
        hash[4] = (byte)(int)(time >>> 16);
        hash[5] = (byte)(int)(time >>> 8);
        hash[6] = (byte)(int)time;
        hash[7] = (byte)(int)time;
        securid_hash_data(hash, k);
    }

     byte hex(byte c){
        byte n = (byte)(c - 48);
        if(n < 10)
            return n;
        n -= 7;
        if(n<=9 || n>=16)
        	n -= 32;
        return n;
    }

     String HextoString(byte b){
        String s = new String();
        s = String.valueOf((byte)((b & 0xff) >> 4)) + String.valueOf((byte)(b & 0xf));
        return s;
    }

    
    /**
     * 生成新密码
     * @param time	
     * @param realSeed 经过还原的seed
     * @param codeLen	密码长度
     * @param period	时间周期
     * @return
     */
    public  String calcPRN(long time, byte realSeed[], int codeLen, int period){
    	String pwd = null;
        if(bFirst != 0){
            bFirst = '\0';
            long t1 = ((time / (long)period & -2L) * (long)period) / 30L;
            securid_hash_time(t1, hi, realSeed);
        }
                
        if((int)(time / (long)period & 1L) != 0){
            if(codeLen == 6)
            	pwd = HextoString(hi[3]) + HextoString(hi[4]) + HextoString(hi[5]);
            else
            	pwd = HextoString(hi[4]) + HextoString(hi[5]) + HextoString(hi[6]) + HextoString(hi[7]);
        }else{
            long t1 = ((time / (long)period & -2L) * (long)period) / 30L;
            securid_hash_time(t1, hi, realSeed);
            if(codeLen == 6)
            	pwd = HextoString(hi[0]) + HextoString(hi[1]) + HextoString(hi[2]);
            else
            	pwd = HextoString(hi[0]) + HextoString(hi[1]) + HextoString(hi[2]) + HextoString(hi[3]);
        }
        return pwd;
    }
}