package live.citrus.pulse.http;

import live.citrus.pulse.file.CPFileSize;

/**
 * Http通信データ保持
 * 
 * @author take64
 *
 */
public class CPHttpData
{
    public String host;
    public String uri;
    public String method;
    public String contentType;
    public byte[] query;
    public byte[] body;
    
    
    /**
     * constructor
     * 
     * @param host
     * @param uri
     * @param method
     * @param contentType
     * @param body
     */
    public CPHttpData(String host, String uri, String method, String contentType, byte[] query, byte[] body)
    {
        this.host = host;
        this.uri = uri;
        this.method = method;
        this.contentType = contentType;
        this.query = query;
        this.body = body;
    }
    
    public String toString()
    {
        return this.host + " -> " 
                + this.uri + " :: " 
                + this.method + " : " 
                + this.contentType + " : " 
                + CPFileSize.sizeString(body);
    }
    
}
