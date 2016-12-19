package org.github.dtsopensource.schedule;

/**
 * @author ligaofeng 2016年12月14日 上午11:16:52
 */
public interface IJobNode {

    /**
     * 开启节点
     * 
     * @return
     */
    public boolean startNode();

    /**
     * 停止节点
     */
    public void stopNode();

    /**
     * 销毁节点
     */
    public void destroyNode();

}
