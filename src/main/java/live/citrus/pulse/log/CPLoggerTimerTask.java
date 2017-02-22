package live.citrus.pulse.log;

import java.util.Date;

public class CPLoggerTimerTask
{
    /** ログタグ **/
    public String name;
    
    /** 開始時間 **/
    public Date startDate;
    
    /** 終了時間 **/
    public Date endDate;
    
    /** 経過時間(ミリ秒) **/
    public Long millisecond;
    
    
    /**
     * constructor
     * 
     * @param name
     */
    public CPLoggerTimerTask(String name)
    {
        this.name = name;
        this.millisecond = Long.valueOf(0);
    }
    
    /**
     * 計測開始
     */
    public void start()
    {
        this.startDate = new Date();
        this.endDate = new Date();
    }
    
    /**
     * 計測終了
     */
    public void end()
    {
        this.endDate = new Date();
        this.millisecond = Long.valueOf(this.endDate.getTime() - this.startDate.getTime());
    }
}
