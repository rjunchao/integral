package com.xbkj.common.jdbc.framework.generator;


/**

 * 数据库的主键生成器
 * @nopublish
 */
public interface IdGenerator {
    /**
     * 得到当前公司的PK
     * @return
     */
    public abstract String generate() ;

    /**
     * 根据传入的公司得到PK
     * @param pk_corp
     * @return
     */
    public abstract String generate(String pk_corp) ;

    /**
     * 根据传入的公司和数量得到PK数组
     * @param pk_corp  公司名称
     * @param amount   PK数量
     * @return
     * @throws Exception  PK数组
     */
    public abstract String[] generate(String pk_corp, int amount)
            throws Exception;

    /**
     * 根据传入的公数量得到PK数组
     * @param amount PK数量
     * @return  PK数组
     */
    public abstract String[] generate(int amount) ;
}
