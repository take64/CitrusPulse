package live.citrus.pulse.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CPLoggerTimer
{
    // タスクリスト
    public static List<CPLoggerTimerTask> tasks = Collections.synchronizedList(new ArrayList<CPLoggerTimerTask>());


    /**
     * 計測開始
     */
    public static void start()
    {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String name = stackTraceElement.getFileName().replaceAll(".java", "") + "." + stackTraceElement.getMethodName();
        
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
     */
    public static void end()
    {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String name = stackTraceElement.getFileName().replaceAll(".java", "") + "." + stackTraceElement.getMethodName();
        
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
     * @param sortColumn
     */
    public static void output(String sortColumn)
    {
        // タスクリスト
        List<CPLoggerTimerTask> taskList = CPLoggerTimer.tasks;
    
        synchronized (taskList)
        {
            // 合計msec
            double total = 0;
            
            // セパレータ
            String separater0 = "----";
            String separater1 = "--------------------------------------------------------";
            String separater2 = "----------------";
            String separater3 = "----------------";
            
            // ヘッダ
            CPLogger.debug("-%4s-%56s-%16s-%16s-%16s-\n", separater0, separater1, separater2, separater2, separater3);
            CPLogger.debug("|%-4s|%-56s|%-16s|%-16s|%-16s|\n", "No", "Name", "Total Count", "Total Time(sec)", "Avg Time(sec)");
            CPLogger.debug(" %4s %56s %16s %16s %16s \n", separater0, separater1, separater2, separater2, separater3);
            
            // ソート
            if(sortColumn.equals("totalMillisecond") == true)
            {
                taskList.sort(new Comparator<CPLoggerTimerTask>() {
                    public int compare(CPLoggerTimerTask o1, CPLoggerTimerTask o2)
                    {
                        return o1.totalMillisecond.compareTo(o2.totalMillisecond);
                    }                    
                });
            }
            
            // 内容
            int size = taskList.size();
            for(int i = 0; i < size; i++)
            {
                CPLoggerTimerTask task = taskList.get(i);
                double totalMsec = task.totalMillisecond.doubleValue();
                double avgMsec = task.avgMillisecond.doubleValue();
                String name = task.name;
                if(name.length() > 56) { name = name.substring(0, 56); }
                CPLogger.debug("|%4s|%-56s|%16s|%16s|%16s|\n",
                        String.valueOf(i+1),
                        name, 
                        task.totalCount.toString(),
                        String.format("%1$.3f sec", (totalMsec / 1000)),
                        String.format("%1$.3f sec", (avgMsec / 1000))
                        );
                total += totalMsec;
            }
            CPLogger.debug(" %4s %56s %16s %16s %16s \n", separater0, separater1, separater2, separater2, separater3);
            
            // フッタ
            CPLogger.debug("|%4s|%56s|%16s|%16s|%16s|\n", "", "", "Total", String.format("%1$.3f sec", (total / 1000)), "");
            CPLogger.debug("-%4s-%56s-%16s-%16s-%16s-\n", separater0, separater1, separater2, separater2, separater3);
        }
    }
    
    /**
     * 計測のリセット
     */
    public static void reset()
    {
        CPLoggerTimer.tasks.clear();
    }

}
