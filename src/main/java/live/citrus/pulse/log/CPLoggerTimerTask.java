package live.citrus.pulse.log;

import java.util.Date;

import live.citrus.pulse.variable.numeric.CPNumericUtils;

public class CPLoggerTimerTask
{
    /** ログタグ **/
    public String name;
    
    /** 開始時間 **/
    private Date startDate;
    
    /** 終了時間 **/
    private Date endDate;
    
    /** 経過時間(ミリ秒) **/
    private Long millisecond;
    
    /** 合計呼び出し回数 **/
    public Long totalCount = Long.valueOf(0);
    
    /** 合計経過時間(ミリ秒) **/
    public Long totalMillisecond = Long.valueOf(0);
    
    /** 平均経過時間(ミリ秒) **/
    public Double avgMillisecond = Double.valueOf(0);
    
    
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
        
        this.totalCount = CPNumericUtils.plus(this.totalCount, Long.valueOf(1));
        this.totalMillisecond = CPNumericUtils.plus(this.totalMillisecond, this.millisecond);
        this.avgMillisecond = CPNumericUtils.divide(this.totalMillisecond, this.totalCount);
    }
}
