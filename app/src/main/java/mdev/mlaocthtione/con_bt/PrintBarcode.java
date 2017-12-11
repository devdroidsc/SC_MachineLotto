package mdev.mlaocthtione.con_bt;

import android.util.Log;

import java.util.Arrays;

public class PrintBarcode {
    private static final String TAG = "WoosimService";
    private static final boolean D = true;
    private static final byte ESC = 27;
    private static final byte GS = 29;
    public static final int UPC_A = 65;
    public static final int UPC_E = 66;
    public static final int EAN13 = 67;
    public static final int EAN8 = 68;
    public static final int CODE39 = 69;
    public static final int ITF = 70;
    public static final int CODEBAR = 71;
    public static final int CODE93 = 72;
    public static final int CODE128 = 73;
    private static final int PDF417 = 0;
    private static final int DATAMATRIX = 1;
    private static final int QR_CODE = 2;
    private static final int MICRO_PDF417 = 3;
    private static final int TRUNC_PDF417 = 4;
    private static final int MAXICODE = 5;

    public PrintBarcode() {
    }

    public static byte[] createBarcode(int type, int width, int height, boolean HRI, byte[] data) {
        Log.d("WoosimService", "createBarcode() with barcode type:" + type);
        if(!validateBarcodeParameter(width, height, type, data)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] cmd = new byte[]{(byte)29, (byte)119, (byte)width, (byte)29, (byte)104, (byte)height, (byte)29, (byte)72, (byte)(HRI?1:0), (byte)29, (byte)107, (byte)type, (byte)data.length};
            buffer.append(cmd, 0, cmd.length);
            buffer.append(data, 0, data.length);
            return buffer.toByteArray();
        }
    }

    private static boolean validateBarcodeParameter(int width, int height, int type, byte[] code) {
        if(width < 1 || width > 8) {
            Log.w("WoosimService", "Invalid parameter at barcode width");
        }

        if(height >= 0 && height <= 255) {
            int i;
            switch(type) {
                case 65:
                case 66:
                    if(code.length < 11 || code.length > 12) {
                        Log.e("WoosimService", "Invalid barcode length");
                        return false;
                    } else {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] < 48 || code[i] > 57) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }
                case 67:
                    if(code.length < 11 || code.length > 13) {
                        Log.e("WoosimService", "Invalid barcode length");
                        return false;
                    } else {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] < 48 || code[i] > 57) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }
                case 68:
                    if(code.length >= 7 && code.length <= 8) {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] < 48 || code[i] > 57) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }

                    Log.e("WoosimService", "Invalid barcode length");
                    return false;
                case 69:
                    if(code.length < 1 || code.length > 255) {
                        Log.e("WoosimService", "Invalid barcode length");
                        return false;
                    } else {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] != 32 && code[i] != 36 && code[i] != 37 && code[i] != 43 && code[i] != 45 && code[i] != 46 && code[i] != 47 && (48 > code[i] || code[i] > 57) && (65 > code[i] || code[i] > 90)) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }
                case 70:
                    if(code.length >= 1 && code.length <= 255) {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] < 48 || code[i] > 57) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }

                    Log.e("WoosimService", "Invalid barcode length");
                    return false;
                case 71:
                    if(code.length < 1 || code.length > 255) {
                        Log.e("WoosimService", "Invalid barcode length");
                        return false;
                    } else {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] != 36 && code[i] != 43 && code[i] != 45 && code[i] != 46 && code[i] != 47 && code[i] != 58 && (48 > code[i] || code[i] > 57) && (65 > code[i] || code[i] > 68)) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }
                case 72:
                    if(code.length >= 1 && code.length <= 255) {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] < 0 || code[i] > 127) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }

                    Log.e("WoosimService", "Invalid barcode length");
                    return false;
                case 73:
                    if(code.length < 2 || code.length > 255) {
                        Log.e("WoosimService", "Invalid barcode length");
                        return false;
                    } else {
                        for(i = 0; i < code.length; ++i) {
                            if(code[i] != 193 && code[i] != 194 && code[i] != 195 && code[i] != 196 && (code[i] < 0 || code[i] > 127)) {
                                Log.e("WoosimService", "Invalid barcode value");
                                return false;
                            }
                        }

                        return true;
                    }
                default:
                    Log.e("WoosimService", "Invalid barcode type");
                    return false;
            }
        } else {
            Log.e("WoosimService", "Invalid parameter at barcode height");
            return false;
        }
    }

    public static byte[] create2DBarcodePDF417(int width, int column, int level, int ratio, boolean HRI, byte[] data) {
        if(!validate2DBarcodeParameter(width, column, level, ratio, 0)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] cmd = new byte[]{(byte)29, (byte)119, (byte)width, (byte)29, (byte)72, (byte)(HRI?1:0)};
            byte[] barcode = make2DBarcode(0, column, level, ratio, data);
            buffer.append(cmd, 0, cmd.length);
            buffer.append(barcode, 0, barcode.length);
            return buffer.toByteArray();
        }
    }

    public static byte[] create2DBarcodeDataMatrix(int height, int width, int size, byte[] data) {
        if(!validate2DBarcodeParameter(2, height, width, size, 1)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] barcode = make2DBarcode(1, height, width, size, data);
            buffer.append(barcode, 0, barcode.length);
            return buffer.toByteArray();
        }
    }

    public static byte[] create2DBarcodeQRCode(int version, byte level, int size, byte[] data) {
        if(!validate2DBarcodeParameter(2, version, level, size, 2)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] barcode = make2DBarcode(2, version, level, size, data);
            buffer.append(barcode, 0, barcode.length);
            return buffer.toByteArray();
        }
    }

    public static byte[] create2DBarcodeMicroPDF417(int width, int column, int row, int ratio, byte[] data) {
        if(!validate2DBarcodeParameter(width, column, row, ratio, 3)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] cmd = new byte[]{(byte)29, (byte)119, (byte)width};
            byte[] barcode = make2DBarcode(3, column, row, ratio, data);
            buffer.append(cmd, 0, cmd.length);
            buffer.append(barcode, 0, barcode.length);
            return buffer.toByteArray();
        }
    }

    public static byte[] create2DBarcodeTruncPDF417(int width, int column, int level, int ratio, boolean HRI, byte[] data) {
        if(!validate2DBarcodeParameter(width, column, level, ratio, 4)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] cmd = new byte[]{(byte)29, (byte)119, (byte)width, (byte)29, (byte)72, (byte)(HRI?1:0)};
            byte[] barcode = make2DBarcode(4, column, level, ratio, data);
            buffer.append(cmd, 0, cmd.length);
            buffer.append(barcode, 0, barcode.length);
            return buffer.toByteArray();
        }
    }

    public static byte[] create2DBarcodeMaxicode(int mode, byte[] data) {
        if(!validate2DBarcodeParameter(2, mode, 0, 0, 5)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] barcode = make2DBarcode(5, mode, 0, 0, data);
            buffer.append(barcode, 0, barcode.length);
            return buffer.toByteArray();
        }
    }

    private static byte[] make2DBarcode(int type, int arg1, int arg2, int arg3, byte[] codeData) {
        Log.d("WoosimService", "make2DBarcode() with barcode type:" + type);
        ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
        int length = codeData.length;
        byte low = (byte)(length & 255);
        byte high = (byte)(length >> 8 & 255);
        byte[] cmd = new byte[]{(byte)29, (byte)90, (byte)type, (byte)27, (byte)90, (byte)arg1, (byte)arg2, (byte)arg3, low, high};
        buffer.append(cmd, 0, cmd.length);
        buffer.append(codeData, 0, codeData.length);
        return buffer.toByteArray();
    }

    private static boolean validate2DBarcodeParameter(int width, int arg1, int arg2, int arg3, int type) {
        if(width < 1 || width > 8) {
            Log.w("WoosimService", "Invalid parameter at barcode width");
        }

        switch(type) {
            case 0:
                if(arg1 < 1 || arg1 > 30) {
                    Log.e("WoosimService", "Invalid PDF417 column");
                    return false;
                }

                if(arg2 < 0 || arg2 > 8) {
                    Log.e("WoosimService", "Invalid PDF417 security level");
                    return false;
                }

                if(arg3 < 2 || arg3 > 5) {
                    Log.e("WoosimService", "Invalid PDF417 horizontal and vertical ratio");
                    return false;
                }
                break;
            case 1:
                if(arg3 >= 1 && arg3 <= 8) {
                    break;
                }

                Log.e("WoosimService", "Invalid DATAMATRIX module size");
                return false;
            case 2:
                if(arg1 >= 0 && arg1 <= 40) {
                    if((byte)arg2 != 76 && (byte)arg2 != 77 && (byte)arg2 != 81 && (byte)arg2 != 72) {
                        Log.e("WoosimService", "Invalid QR-CODE EC level");
                        return false;
                    }

                    if(arg3 >= 1 && arg3 <= 8) {
                        break;
                    }

                    Log.e("WoosimService", "Invalid QR-CODE module size");
                    return false;
                }

                Log.e("WoosimService", "Invalid QR-CODE version");
                return false;
            case 3:
                if(arg1 < 1 || arg1 > 4) {
                    Log.e("WoosimService", "Invalid Micro PDF417 column");
                    return false;
                }

                if((arg2 < 4 || arg2 > 44) && arg2 != 0) {
                    Log.e("WoosimService", "Invalid Micro PDF417 row");
                    return false;
                }

                if(arg3 < 2 || arg3 > 5) {
                    Log.e("WoosimService", "Invalid Micro PDF417 horizontal and vertical ratio");
                    return false;
                }
                break;
            case 4:
                if(arg1 < 1 || arg1 > 4) {
                    Log.e("WoosimService", "Invalid Truncated PDF417 column");
                    return false;
                }

                if(arg2 < 0 || arg2 > 8) {
                    Log.e("WoosimService", "Invalid Truncated PDF417 security level");
                    return false;
                }

                if(arg3 < 2 || arg3 > 5) {
                    Log.e("WoosimService", "Invalid Micro Truncated PDF417 horizontal and vertical ratio");
                    return false;
                }
                break;
            case 5:
                if(arg1 >= 2 && arg1 <= 6) {
                    break;
                }

                Log.e("WoosimService", "Invalid Maxicode mode");
                return false;
            default:
                Log.e("WoosimService", "Invalid 2D barcode type");
                return false;
        }

        return true;
    }

    public static byte[] createGS1Databar(int type, int seg, byte[] data) {
        Log.d("WoosimService", "createGS1Databar() with barcode type:" + type);
        byte[] cdata;
        if(data[data.length - 1] == 0) {
            cdata = Arrays.copyOf(data, data.length - 1);
        } else {
            cdata = Arrays.copyOf(data, data.length);
        }

        if(!validateGS1Parameter(type, seg, cdata)) {
            return null;
        } else {
            ByteArrayBuffer buffer = new ByteArrayBuffer(1024);
            byte[] cmd = new byte[]{(byte)29, (byte)49, (byte)type, (byte)seg};
            buffer.append(cmd, 0, cmd.length);
            buffer.append(cdata, 0, cdata.length);
            buffer.append(0);
            return buffer.toByteArray();
        }
    }

    private static boolean validateGS1Parameter(int type, int seg, byte[] data) {
        if(type >= 0 && type <= 6) {
            switch(type) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    if(data.length >= 1 && data.length <= 13) {
                        for(int i = 0; i < data.length; ++i) {
                            if(data[i] < 48 || data[i] > 57) {
                                Log.e("WoosimService", "Invalid data:" + data[i]);
                                return false;
                            }
                        }

                        return true;
                    }

                    Log.e("WoosimService", "Invalid data length:" + data.length);
                    return false;
                case 5:
                default:
                    break;
                case 6:
                    if(seg < 2 || seg > 20) {
                        Log.e("WoosimService", "Invalid parameter at GS1 databar segment per row");
                        return false;
                    }

                    if(seg % 2 != 0) {
                        Log.e("WoosimService", "GS1 databar segment per row is not even number");
                        return false;
                    }
            }

            return true;
        } else {
            Log.e("WoosimService", "Invalid parameter at GS1 databar type");
            return false;
        }
    }
}

