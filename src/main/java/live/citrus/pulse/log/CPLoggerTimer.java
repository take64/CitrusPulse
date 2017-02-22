package live.citrus.pulse.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import live.citrus.pulse.variable.date.CPDateUtils;

public class CPLoggerTimer
{
    /** タスクリスト **/
//    public static List<CPLoggerTimerTask> tasks = new ArrayList<CPLoggerTimerTask>();
    public static List<CPLoggerTimerTask> tasks = Collections.synchronizedList(new ArrayList<CPLoggerTimerTask>());
    
    
    /**
     * 計測開始
     * 
     * @param name
     */
    public static void start(String name)
    {
        CPLoggerTimerTask currentTask = CPLoggerTimer.call(name);
        if(currentTask == null)
        {
            currentTask = new CPLoggerTimerTask(name);
            CPLoggerTimer.tasks.add(currentTask);
        }
        currentTask.start();
        
    }

    /**
     * 計測終了
     * 
     * @param name
     */
    public static void end(String name)
    {
        CPLoggerTimerTask currentTask = CPLoggerTimer.call(name);
        if(currentTask != null)
        {
            currentTask.end();
        }
    }

    /**
     * 計測タスクの取得
     * 
     * @param name
     */
    public static CPLoggerTimerTask call(String name)
    {
        CPLoggerTimerTask currentTask = null;
        for(CPLoggerTimerTask task : CPLoggerTimer.tasks)
        {
            if(task.name.equals(name) == true)
            {
                currentTask = task;
            }
        }
        return currentTask;
    }

    /**
     * 計測タスク出力
     * 
     * @param name
     */
    public static void output(String sortColumn)
    {
        // タスクリスト
        List<CPLoggerTimerTask> taskList = CPLoggerTimer.tasks;
    
        synchronized (taskList)
        {
            // 合計msec
            long total = 0;
            
            // セパレータ
            String separater0 = "----";
            String separater1 = "------------------------------------------------";
            String separater2 = "------------------------";
            String separater3 = "------------";
            
            // ヘッダ
            CPLogger.debug("-%4s-%48s-%24s-%24s-%12s-\n", separater0, separater1, separater2, separater2, separater3);
            CPLogger.debug("|%-4s|%-48s|%-24s|%-24s|%-12s|\n", "No", "Name", "Start Date", "End Date", "Time(sec)");
            CPLogger.debug(" %4s %48s %24s %24s %12s \n", separater0, separater1, separater2, separater2, separater3);
            
            // ソート
            if(sortColumn.equals("millisecond") == true)
            {
                taskList.sort(new Comparator<CPLoggerTimerTask>() {
                    public int compare(CPLoggerTimerTask o1, CPLoggerTimerTask o2)
                    {
                        if(o1.millisecond.longValue() > o2.millisecond.longValue()) { return -1; }
                        else if(o1.millisecond.longValue() == o2.millisecond.longValue()) { return 0; }
                        return 1;
                    }                    
                });
            }
            
            // 内容
            int size = taskList.size();
            for(int i = 0; i < size; i++)
            {
                CPLoggerTimerTask task = taskList.get(i);
                long msec = task.millisecond.longValue();
                String name = task.name;
                if(name.length() > 48) { name = name.substring(0, 48); }
                CPLogger.debug("|%4s|%-48s|%-24s|%-24s|%12s|\n",
                        String.valueOf(i+1),
                        name, 
                        CPDateUtils.format("yyyy/MM/dd kk:mm:ss.SSS", task.startDate),
                        CPDateUtils.format("yyyy/MM/dd kk:mm:ss.SSS", task.endDate),
                        String.format("%1$.3f sec", ((double)msec / 1000))
                        );
                total += msec;
            }
            CPLogger.debug(" %4s %48s %24s %24s %12s \n", separater0, separater1, separater2, separater2, separater3);
            
            // フッタ
            CPLogger.debug("|%4s|%48s|%24s|%24s|%12s|\n", "", "", "", "Total", String.format("%1$.3f sec", ((double)total / 1000)));
            CPLogger.debug("-%4s-%48s-%24s-%24s-%12s-\n", separater0, separater1, separater2, separater2, separater3);
        }
    }
    
    /**
     * 計測のリセット
     */
    public static void reset()
    {
        CPLoggerTimer.tasks = Collections.synchronizedList(new ArrayList<CPLoggerTimerTask>());
    }

}
