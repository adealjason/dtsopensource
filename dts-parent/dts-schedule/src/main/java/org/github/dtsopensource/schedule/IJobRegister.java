package org.github.dtsopensource.schedule;

/**
 * @author ligaofeng 2016年12月14日 上午11:18:13
 */
public interface IJobRegister {

    /**
     * @return
     */
    public boolean register();

    /**
     * 
     */
    public void stop();

    /**
     * 
     */
    public void destroy();
}
