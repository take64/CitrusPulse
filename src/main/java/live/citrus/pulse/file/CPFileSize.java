package live.citrus.pulse.file;

import java.text.DecimalFormat;

/**
 * ファイルサイズ関連メソッド
 * 
 * @author take64
 *
 */
public class CPFileSize
{
    // フォーマッタ
    private static DecimalFormat formatter = new DecimalFormat("#,###.###");
    
    
    /**
     * byte配列のサイズを取得
     * 
     * @param value
     * @return
     */
    public static int size(byte[] value)
    {
        return value.length;
    }
    
    
    /**
     * byte配列のサイズを文字列で取得
     * 
     * @param value
     * @return
     */
    public static String sizeString(byte[] value)
    {
        return CPFileSize.sizeString(value, true);
    }
    
    
    /**
     * Stringのサイズを文字列で取得
     * 
     * @param value
     * @return
     */
    public static String sizeString(String value)
    {
        return CPFileSize.sizeString(value, true);
    }
    
    /**
     * Stringのサイズを文字列で取得
     * 
     * @param value
     * @param contraction true:MByteやMByteに短縮する
     * @return
     */
    public static String sizeString(String value, boolean contraction)
    {
        return CPFileSize.sizeString(value.getBytes(), contraction);
    }
    
    
    /**
     * byte配列のサイズを文字列で取得
     * 
     * @param value
     * @param contraction true:MByteやMByteに短縮する
     * @return
     */
    public static String sizeString(byte[] value, boolean contraction)
    {
        double size = CPFileSize.size(value);

        String result = CPFileSize.formatSizeString(size, contraction);
        return result;
    }
    
    /**
     * 数値からサイズ文字列に変換
     * 
     * @param size
     * @param contraction true:MByteやMByteに短縮する
     * @return
     */
    public static String formatSizeString(double size, boolean contraction)
    {
        String mark = "";
        
        // 短縮する場合
        if(contraction == true)
        {
            // KByte
            if(size >= 1024)
            {
                size = size / 1024;
                mark = "K";
            }
            // MByte
            if(size >= 1024)
            {
                size = size / 1024;
                mark = "M";
            }
            // GByte
            if(size >= 1024)
            {
                size = size / 1024;
                mark = "G";
            }
        }
        
        String result = CPFileSize.formatter.format(size) + mark + "Byte";
        return result;
    }
}
